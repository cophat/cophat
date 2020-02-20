package com.matheus.cophat.feature.questions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.matheus.cophat.R
import com.matheus.cophat.data.local.entity.*
import com.matheus.cophat.data.presenter.ItemSubQuestionPresenter
import com.matheus.cophat.data.presenter.QuestionnairePresenter
import com.matheus.cophat.data.presenter.SubQuestionPresenter
import com.matheus.cophat.data.repository.QuestionsRepository
import com.matheus.cophat.helper.ResourceManager
import com.matheus.cophat.helper.visibleOrGone
import com.matheus.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubQuestionViewModel(
    private val repository: QuestionsRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    lateinit var presenter: SubQuestionPresenter
    val isPrimaryButtonEnabled = MutableLiveData<Boolean>()
    val statement = MutableLiveData<String>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                repository.getGender()?.let {
                    val gender = if (it == GenderType.MALE.genderType) {
                        GenderType.MALE
                    } else {
                        GenderType.FEMALE
                    }
                    statement.postValue(getStatement(gender))
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun getStatement(gender: GenderType): String? {
        return if (presenter.subQuestion.statement.isNullOrEmpty()) {
            if (gender == GenderType.MALE) {
                presenter.subQuestion.statementMale
            } else {
                presenter.subQuestion.statementFemale
            }
        } else {
            presenter.subQuestion.statement
        }
    }

    fun getAlternatives(): List<ItemSubQuestionPresenter>? {
        return presenter.subQuestion.alternatives
            ?.map { entry ->
                ItemSubQuestionPresenter().apply {
                    id = entry.value.id
                    type = entry.value.type
                    description = getDescriptionByType(entry.value)
                    descriptionVisibility = getDescriptionVisibility(entry.value)
                    alternativeIsEnabled = entry.value.type == SubAnswerType.OPEN
                    otherVisibility = getOtherVisibility(entry.value)
                    chosenSubAnswer = AnswerType.NEVER
                    isPrimaryButtonEnabled.postValue(isValidItem(this))
                }
            }
    }

    private fun getDescriptionByType(alternative: Alternative): String? {
        return when {
            alternative.type == SubAnswerType.OTHER -> resourceManager.getString(R.string.other)
            alternative.type == SubAnswerType.OPEN -> resourceManager.getString(R.string.describe)
            else -> alternative.description
        }
    }

    private fun getDescriptionVisibility(alternative: Alternative): Int {
        return (alternative.type != SubAnswerType.OPEN &&
                alternative.type != SubAnswerType.OTHER).visibleOrGone()
    }

    private fun getOtherVisibility(alternative: Alternative): Int {
        return (alternative.type == SubAnswerType.OPEN ||
                alternative.type == SubAnswerType.OTHER).visibleOrGone()
    }

    fun isValidItem(item: ItemSubQuestionPresenter): Boolean {
        return if (item.type == SubAnswerType.OPEN ||
            (item.type == SubAnswerType.OTHER && item.chosenSubAnswer != AnswerType.NEVER)
        ) {
            !item.other.isNullOrEmpty()
        } else {
            true
        }
    }

    suspend fun saveAlternatives(subAnswers: List<ItemSubQuestionPresenter>) {
        try {
            isLoading.postValue(true)

            repository.addChild(presenter.subAnswerPath, SubAnswer(presenter.subAnswerId))

            val alternativePath = generateAlternativesPath()
            for (subAnswer in subAnswers) {
                repository.addChild(alternativePath,
                    AlternativeAnswer().apply {
                        id = subAnswer.id
                        other = subAnswer.other
                        chosenSubAnswer = subAnswer.chosenSubAnswer
                    })
            }
        } catch (e: DatabaseException) {
            handleError.postValue(e)
        } finally {
            isLoading.postValue(false)
        }
    }

    private suspend fun generateAlternativesPath(): String {
        return presenter.subAnswerPath + "/" + getSubAnswerKey() + "/alternatives"
    }

    private suspend fun getSubAnswerKey(): String? {
        val questionnaire = getUpdatedQuestionnaire()
        return getApplication(questionnaire)
            ?.answers
            ?.values
            ?.firstOrNull { it.id == presenter.answerId }
            ?.subAnswers
            ?.entries
            ?.firstOrNull { it.value.id == presenter.subAnswerId }
            ?.key
    }

    private suspend fun getUpdatedQuestionnaire(): QuestionnairePresenter? {
        return repository.getFamilyId()?.let {
            repository.getQuestionnaireByFamilyId(it)
        }
    }

    private fun getApplication(questionnairePresenter: QuestionnairePresenter?): ApplicationEntity? {
        return if (isChildren) {
            questionnairePresenter?.questionnaire?.childApplication
        } else {
            questionnairePresenter?.questionnaire?.parentApplication
        }
    }
}