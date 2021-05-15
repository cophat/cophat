package com.jodi.cophat.feature.questions.viewmodel

import androidx.fragment.app.FragmentManager.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.data.presenter.*
import com.jodi.cophat.data.repository.*
import com.jodi.cophat.feature.questions.fragment.QuestionsFragment
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsView
import com.jodi.cophat.ui.base.view.ThermometerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList

class QuestionsViewModel(
    private val repository: QuestionsRepository,
    private val repositoryQuestionnaire: QuestionnairesRepository,
    private val resourceManager: ResourceManager,
    private val repositoryGenerateCode: GenerateCodeRepository
) : BaseViewModel() {

    val questions = ArrayList<Question>()
    val presenter = QuestionsPresenter()
    val subQuestion = MutableLiveData<SubQuestionPresenter>()
    val completeQuestionnaire = MutableLiveData<Int>()
    private var position = 0
    private var subQuestionPosition = 0
    lateinit var gender: String
    private var application: ApplicationEntity? = null
    private var applicationLocally: ApplicationEntity? = null
    private var questionnairePresenter: QuestionnairePresenter? = null
    private var hasSubQuestionToRespond: Boolean = false

    var continueQuestionnaire = false
    var identificationCode: String? = ""
    var pendingItem: ItemPendingPresenter? = null

    val applicationPending = MutableLiveData<ApplicationEntity>()
    val pendingPresenter = MutableLiveData<PendingPresenter>()
    val statusPending = MutableLiveData<String>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                if(continueQuestionnaire == true){
                    getPendingQuestionnaires()
                }else{
                    getApplicationLocally()
                    getQuestions()
                    getUpdatedQuestionnaire()
                    retrievePositionInQuestionnaire()
                    verifyStep()
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun getApplicationLocally(){
        runBlocking {
            applicationLocally = repository.getApplication()
            if(applicationLocally != null){
                identificationCode = applicationLocally?.identificationCode
                pendingItem = ItemPendingPresenter(
                    keyQuestionnaire = applicationLocally?.keyQuestionnaire!!,
                    parentPosition = applicationLocally?.parentPosition!!,
                    identificationCode = applicationLocally?.identificationCode!!,
                    typeInterviewee = applicationLocally?.typeInterviewee!!,
                    name = applicationLocally?.name!!,
                    admin = applicationLocally?.admin,
                    hospital = applicationLocally?.hospital,
                    date = applicationLocally?.date
                )
            }
        }
    }

    fun setValues(item: ItemPendingPresenter) {
        viewModelScope.launch(context = Dispatchers.IO) {
            identificationCode = item.identificationCode
            pendingItem = item
            getUpdatedQuestionnaire()
        }
    }

    suspend fun getPendingQuestionnaires() {
        val list = repositoryQuestionnaire.getPendingQuestionnaires()
        for (i in list) {
            i.pendingDividerVisibility = (i != list.last()).visibleOrGone()
        }

        pendingPresenter.postValue(
            PendingPresenter(list.isEmpty().visibleOrGone(), list)
        )
    }

    private suspend fun getQuestions() {
        val form = if (isChildren && !(pendingItem?.typeInterviewee?.contains("Responsável -")!!) || pendingItem?.typeInterviewee.equals("Paciente")) {
            repository.getForms(FormType.CHILDREN)
        } else {
            repository.getForms(FormType.PARENTS)
        }
        form?.let { questions.addAll(it) }
    }

    private fun getUpdatedQuestionnaire() {
        runBlocking {
            if(identificationCode == null || identificationCode == "") {
                repository.getIdentificationCode()?.let {
                    identificationCode = it
                    questionnairePresenter = repository.getQuestionnaireByIdentificationCode(it)
                }
            }else{
                questionnairePresenter = repository.getQuestionnaireByIdentificationCode(identificationCode)
            }
            getApplication()
        }
    }

    private fun getApplication() {
        runBlocking {
            if(pendingItem?.typeInterviewee.isNullOrEmpty()){
                application = (if (isChildren) {
                    questionnairePresenter?.questionnaire?.childApplication
                } else {
                    questionnairePresenter?.questionnaire?.parentApplication?.last()
                })
            }else {
                if (pendingItem?.typeInterviewee.equals("Paciente")) {
                    application = questionnairePresenter?.questionnaire?.childApplication
                } else {
                    application =
                        questionnairePresenter?.questionnaire?.parentApplication?.get(pendingItem?.parentPosition!!)
                }
            }
            if(application != null){
                application?.keyQuestionnaire = pendingItem?.keyQuestionnaire!!
                application?.parentPosition = pendingItem?.parentPosition!!
                application?.typeInterviewee = pendingItem?.typeInterviewee!!
                application?.name = pendingItem?.name!!
                repositoryGenerateCode.saveApplicationLocally(application!!)
                applicationPending.postValue(application)
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
        presenter.code = identificationCode
        presenter.state = retrieveState()
        presenter.progress = retrieveProgress()
        presenter.statement = retrieveStatementByGender()
    }

    private suspend fun completeApplication() {
        questionnairePresenter?.let {
            if (isChildren && !(pendingItem?.typeInterviewee?.contains("Responsável -")!!) || pendingItem?.typeInterviewee.equals("Paciente")) {
                it.questionnaire.childApplication?.status = ApplicationStatus.COMPLETED
                it.questionnaire.childApplication?.endHour =
                    Calendar.getInstance().timeInMillis
                repository.updateChildrenQuestionnaire(it)
            } else {
                if(!pendingItem?.typeInterviewee.equals("")){
                    it.questionnaire.parentApplication.get(pendingItem?.parentPosition!!).status = ApplicationStatus.COMPLETED
                    it.questionnaire.parentApplication.get(pendingItem?.parentPosition!!).endHour =
                        Calendar.getInstance().timeInMillis
                }else{
                    it.questionnaire.parentApplication.last().status = ApplicationStatus.COMPLETED
                    it.questionnaire.parentApplication.last().endHour =
                        Calendar.getInstance().timeInMillis
                }
                repository.updateParentQuestionnaire(it)
            }
            completeQuestionnaire.postValue(R.id.action_questionsFragment_to_completeFragment)
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
                }
                if (hasSubQuestionToRespond() && presenter.answer != AnswerType.NEVER) {
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
        if ((isChildren && !(pendingItem?.typeInterviewee?.contains("Responsável -")!!)) || pendingItem?.typeInterviewee.equals("Paciente")) {
            return FirebaseChild.QUESTIONNAIRES.pathName +
                    "/" +
                    questionnairePresenter?.questionnaireFirebaseKey +
                    "/childApplication/answers"
        } else if(!pendingItem?.typeInterviewee.equals("")){
            return FirebaseChild.QUESTIONNAIRES.pathName +
                    "/" +
                    questionnairePresenter?.questionnaireFirebaseKey +
                    "/parentApplication/" + pendingItem?.parentPosition + "/answers"
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

    fun removePending(keyQuestionnaire: String?, position: Int?, typeInterviewee: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)
                if(typeInterviewee.equals("Paciente")){
                    repositoryQuestionnaire.removePendingChild(keyQuestionnaire!!)
                }else{
                    repositoryQuestionnaire.removePendingParent(keyQuestionnaire!!, position!!)
                }
                statusPending.postValue(resourceManager.getString(R.string.success_remove))
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}