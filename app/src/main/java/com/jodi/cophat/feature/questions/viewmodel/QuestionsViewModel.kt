package com.jodi.cophat.feature.questions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.data.presenter.*
import com.jodi.cophat.data.repository.FirebaseChild
import com.jodi.cophat.data.repository.PatientRepository
import com.jodi.cophat.data.repository.QuestionsRepository
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class QuestionsViewModel(private val repository: QuestionsRepository) : BaseViewModel() {

    val questions = ArrayList<Question>()
    val presenter = QuestionsPresenter()
    val subQuestion = MutableLiveData<SubQuestionPresenter>()
    val completeQuestionnaire = MutableLiveData<Int>()
    private var position = 0
    private var subQuestionPosition = 0
    lateinit var gender: String
    private var application: ApplicationEntity? = null
    private var questionnairePresenter: QuestionnairePresenter? = null
    private var hasSubQuestionToRespond: Boolean = false
    private var  identifyCode: String? = null
    private var parentPosition: Int? = null
    private var typeInterviewee: String? = null


    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                getQuestions()
                getUpdatedQuestionnaire()
                getApplication()
                retrievePositionInQuestionnaire()
                verifyStep()
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun setValues(parentPositionParam: String?, typeIntervieweeParam: String?, identifyCodeParam: String?) {
        identifyCode = identifyCodeParam
        parentPosition = parentPositionParam?.toInt()
        typeInterviewee = typeIntervieweeParam
    }

    private suspend fun getQuestions() {
        val form = if (isChildren || typeInterviewee.equals("Paciente")) {
            repository.getForms(FormType.CHILDREN)
        } else {
            repository.getForms(FormType.PARENTS)
        }
        form?.let { questions.addAll(it) }
    }

    private suspend fun getUpdatedQuestionnaire() {
        runBlocking<Unit> {
            if(identifyCode == null) {
                repository.getIdentifyCode()?.let {
                    identifyCode = it
                    questionnairePresenter = repository.getQuestionnaireByIdentifyCode(it)
                }
            }else{
                questionnairePresenter = repository.getQuestionnaireByIdentifyCode(identifyCode)
            }
        }
    }

    private fun getApplication() {
        if(typeInterviewee.isNullOrEmpty()){
            application = if (isChildren) {
                questionnairePresenter?.questionnaire?.childApplication
            } else {
                questionnairePresenter?.questionnaire?.parentApplication?.last()
            }
        }else{
            if(typeInterviewee.equals("Paciente")){
                application = questionnairePresenter?.questionnaire?.childApplication
            }else{
                application = questionnairePresenter?.questionnaire?.parentApplication?.get(parentPosition!!)
            }
        }

    }

    private fun retrievePositionInQuestionnaire() {
        getLastAnswer()?.let { lastAnswer ->
            val lastSubQuestions = questions[lastAnswer.id - 1].subQuestions
            when {
                lastSubQuestions.isNullOrEmpty() -> position = lastAnswer.id
                lastAnswer.subAnswers.isNullOrEmpty() -> {
                    lastAnswer.chosenAnswer?.let { chosenAnswer ->
                        if (chosenAnswer != AnswerType.NEVER) {
                            position = lastAnswer.id - 1
                            generateSubQuestionPresenter()
                        } else {
                            position = lastAnswer.id
                        }
                    }
                }
                else -> {
                    lastAnswer.chosenAnswer?.let { chosenAnswer ->
                        if (chosenAnswer != AnswerType.NEVER) {
                            lastSubQuestions.let { lastSubQuestions ->
                                lastAnswer.subAnswers?.let { lastSubAnswers ->
                                    continueAskingSubQuestions(
                                        lastSubAnswers,
                                        lastSubQuestions,
                                        lastAnswer
                                    )
                                }
                            }
                        } else {
                            position = lastAnswer.id
                        }
                    }
                }
            }
        }
    }

    private fun getLastAnswer(): Answer? {
        return application?.answers?.maxBy { it.value.id }?.value
    }

    private fun continueAskingSubQuestions(
        lastSubAnswers: HashMap<String, SubAnswer>,
        lastSubQuestions: HashMap<String, SubQuestion>,
        lastAnswer: Answer
    ) {
        if (lastSubAnswers.size < lastSubQuestions.size) {
            hasSubQuestionToRespond = true
            subQuestionPosition = lastSubAnswers.size
            position = lastAnswer.id - 1
            generateSubQuestionPresenter()
        } else {
            position = lastAnswer.id
        }
    }

    private suspend fun verifyStep() {
        if (position < questions.size) {
            generatePresenter()
        } else {
            completeApplication()
            repository.clearLocally()
        }
    }

    private fun generatePresenter() {
        presenter.code = identifyCode
        presenter.state = retrieveState()
        presenter.progress = retrieveProgress()
        presenter.statement = retrieveStatementByGender()
    }

    private suspend fun completeApplication() {
        questionnairePresenter?.let {
            if (isChildren || typeInterviewee.equals("Paciente")) {
                it.questionnaire.childApplication?.status = ApplicationStatus.COMPLETED
                it.questionnaire.childApplication?.endHour =
                    Calendar.getInstance().timeInMillis
                repository.updateChildrenQuestionnaire(it)
            } else {
                if(parentPosition != null){
                    it.questionnaire.parentApplication.get(parentPosition!!).status = ApplicationStatus.COMPLETED
                    it.questionnaire.parentApplication.get(parentPosition!!).endHour =
                        Calendar.getInstance().timeInMillis
                }else{
                    it.questionnaire.parentApplication.last().status = ApplicationStatus.COMPLETED
                    it.questionnaire.parentApplication.last().endHour =
                        Calendar.getInstance().timeInMillis
                }
                repository.updateParentQuestionnaire(it)
            }
            if(typeInterviewee != null || parentPosition != null){
                completeQuestionnaire.postValue(R.id.action_pendingFragment_to_completeFragment)
            }else{
                completeQuestionnaire.postValue(R.id.action_questionsFragment_to_completeFragment)
            }
        }
    }

    private fun retrieveState(): String {
        return "${position + 1} / ${questions.size}"
    }

    private fun retrieveProgress(): Int {
        return ((position + 1) / questions.size.toDouble() * 100).toInt()
    }

    private fun retrieveStatementByGender(): String? {
        gender = application?.gender.toString()
        return if (questions[position].statement.isNullOrEmpty()) {
            if (gender.equals(GenderType.MALE.genderType)) {
                questions[position].statementMale
            } else {
                questions[position].statementFemale
            }
        } else {
            questions[position].statement
        }
    }

    fun updateApplication() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                if (!hasSubQuestionToRespond) {
                    repository.addChild(getCurrentAnswerPath(), generateAnswer())
                    getUpdatedQuestionnaire()
                    getApplication()
                }
                if (hasSubQuestionToRespond() &&
                    presenter.answer != AnswerType.NEVER
                ) {
                    generateSubQuestionPresenter()
                } else {
                    continueQuestionnaire()
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun getCurrentAnswerPath(): String {
        if (isChildren || typeInterviewee.equals("Paciente")) {
            return FirebaseChild.QUESTIONNAIRES.pathName +
                    "/" +
                    questionnairePresenter?.questionnaireFirebaseKey +
                    "/childApplication/answers"
        } else if(parentPosition != null){
            return FirebaseChild.QUESTIONNAIRES.pathName +
                    "/" +
                    questionnairePresenter?.questionnaireFirebaseKey +
                    "/parentApplication/" + questionnairePresenter?.questionnaire?.parentApplication?.get(parentPosition!!) + "/answers"
        }else{
            return FirebaseChild.QUESTIONNAIRES.pathName +
                    "/" +
                    questionnairePresenter?.questionnaireFirebaseKey +
                    "/parentApplication/" + questionnairePresenter?.questionnaire?.parentApplication?.lastIndex + "/answers"
        }
    }

    private fun generateAnswer(): Answer {
        questions[position]
        return Answer(position + 1, presenter.answer)
    }

    private fun hasSubQuestionToRespond(): Boolean {
        questions[position].subQuestions?.let {
            if (subQuestionPosition < it.size) {
                return true
            }
        }
        return false
    }

    private suspend fun continueQuestionnaire() {
        if (position + 1 < questions.size) {
            position++
            generatePresenter()
        } else {
            completeApplication()
            repository.clearLocally()
        }
    }

    private fun generateSubQuestionPresenter() {
        val answerKey = application
            ?.answers
            ?.entries
            ?.firstOrNull { it.value.id == position + 1 }
            ?.key

        val subQuestion = questions[position]
            .subQuestions
            ?.values
            ?.firstOrNull { it.id == subQuestionPosition + 1 }

        answerKey?.let {
            subQuestion?.let {
                this.subQuestion.postValue(
                    SubQuestionPresenter(
                        getCurrentAnswerPath() + "/" + answerKey + "/subAnswers",
                        position + 1,
                        subQuestion,
                        subQuestionPosition + 1
                    )
                )
            }
        }
    }
}