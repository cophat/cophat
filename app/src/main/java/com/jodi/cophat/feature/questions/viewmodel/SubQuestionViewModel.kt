package com.jodi.cophat.feature.questions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.data.presenter.ItemSubQuestionPresenter
import com.jodi.cophat.data.presenter.QuestionnairePresenter
import com.jodi.cophat.data.presenter.SubQuestionPresenter
import com.jodi.cophat.data.repository.QuestionsRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubQuestionViewModel(
    private val repository: QuestionsRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    lateinit var presenter: SubQuestionPresenter
    val isPrimaryButtonEnabled = MutableLiveData<Boolean>()
    val statement = MutableLiveData<String>()
    lateinit var gender: String
    private var application: ApplicationEntity? = null
    private lateinit var identifyCode: String

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                application = getApplication(getUpdatedQuestionnaire())

                statement.postValue(getStatement(application?.gender))

            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun getStatement(gender: String?): String? {
        return if (presenter.subQuestion.statement.isNullOrEmpty()) {
            if (gender.equals(GenderType.MALE.genderType)) {
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
            questionnairePresenter?.questionnaire?.parentApplication?.last()
        }
    }
}