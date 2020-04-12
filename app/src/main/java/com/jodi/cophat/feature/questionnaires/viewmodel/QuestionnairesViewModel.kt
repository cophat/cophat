package com.jodi.cophat.feature.questionnaires.viewmodel

import com.google.firebase.database.Query
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.ApplicationStatus
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.data.local.entity.Questionnaire
import com.jodi.cophat.data.presenter.ItemQuestionnairePresenter
import com.jodi.cophat.data.repository.QuestionnairesRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel
import java.time.Duration
import java.time.Instant
import kotlin.time.ExperimentalTime

class QuestionnairesViewModel(
    private val repository: QuestionnairesRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    override fun initialize() {}

    fun getQuery(): Query {
        return repository.getQuery()
    }

    @ExperimentalTime
    fun convertToPresenter(questionnaire: Questionnaire): ItemQuestionnairePresenter {
        val application = retrieveApplication(questionnaire)

        return ItemQuestionnairePresenter(
            applicationId = generateApplicationId(
                questionnaire.familyId,
                application?.patient?.patientName
            ),
            childrenDrawable = generateChildrenDrawable(application?.patient?.gender),
            childrenState = generateChildrenState(questionnaire.childApplication?.status),
            parentsState = generateParentsState(questionnaire.parentApplication?.status),
            applicationsTime = generateApplicationsTime(questionnaire),
            hospital = generateHospital(questionnaire.hospital),
            admin = generateAdmin(questionnaire),
            excelEnabled = generateExcelEnabled(questionnaire),
            questionnaire = questionnaire
        )
    }

    private fun retrieveApplication(questionnaire: Questionnaire): ApplicationEntity? {
        return if (isChildren) {
            questionnaire.childApplication
        } else {
            questionnaire.parentApplication
        }
    }

    private fun generateApplicationId(familyId: String, childName: String?): String {
        return "$familyId - $childName"
    }

    private fun generateChildrenDrawable(gender: String?): Int {
        gender?.let {
            return if (gender == GenderType.MALE.genderType) {
                R.drawable.ic_boy
            } else {
                R.drawable.ic_girl
            }
        }
        return R.drawable.ic_girl
    }

    private fun generateChildrenState(status: ApplicationStatus?): String {
        return when (status) {
            null -> resourceManager.getString(R.string.not_initialized)
            ApplicationStatus.STARTED -> resourceManager.getString(R.string.incomplete)
            ApplicationStatus.COMPLETED -> resourceManager.getString(R.string.complete)
        }
    }

    private fun generateParentsState(status: ApplicationStatus?): String {
        return when (status) {
            null -> resourceManager.getString(R.string.not_initialized)
            ApplicationStatus.STARTED -> resourceManager.getString(R.string.incomplete)
            ApplicationStatus.COMPLETED -> resourceManager.getString(R.string.complete)
        }
    }

    @ExperimentalTime
    private fun generateApplicationsTime(questionnaire: Questionnaire): String {
        var childrenTime = ""
        questionnaire.childApplication?.let { application ->
            application.startHour?.let { startHour ->
                application.endHour?.let { endHour ->
                    childrenTime = formatHour(endHour, startHour)
                }
            }
        }

        var parentsTime = ""
        questionnaire.parentApplication?.let { application ->
            application.startHour?.let { startHour ->
                application.endHour?.let { endHour ->
                    parentsTime = formatHour(endHour, startHour)
                }
            }
        }

        return if (childrenTime.isEmpty() && parentsTime.isNotEmpty()) {
            "$parentsTime P"
        } else if (childrenTime.isNotEmpty() && parentsTime.isEmpty()) {
            "$childrenTime CA"
        } else if (childrenTime.isNotEmpty() && parentsTime.isNotEmpty()) {
            "$childrenTime CA\n$parentsTime P"
        } else {
            ""
        }
    }

    @ExperimentalTime
    private fun formatHour(endHour: Long, startHour: Long): String {

        val iInicial: Instant = Instant.ofEpochMilli(startHour)
        val iFinal: Instant = Instant.ofEpochMilli(endHour)
        val duracao: Duration = Duration.between(iInicial, iFinal)

        return if (duracao.toHours() > 24) {
            resourceManager.getString(R.string.more_days)
        } else {
            "${duracao.toHours()}h${duracao.toMinutes() % 60}m"
        }
    }

    private fun generateHospital(hospital: String?): String {
        return hospital ?: ""
    }

    private fun generateAdmin(questionnaire: Questionnaire): String {
        val childrenAdmin = questionnaire.childApplication?.admin ?: ""
        val parentsAdmin = questionnaire.parentApplication?.admin ?: ""

        return if (childrenAdmin.isEmpty() && parentsAdmin.isNotEmpty()) {
            "${parentsAdmin.substringBefore(" ")} - P"
        } else if (childrenAdmin.isNotEmpty() && parentsAdmin.isEmpty()) {
            "${childrenAdmin.substringBefore(" ")} - CA"
        } else if (childrenAdmin.isNotEmpty() && parentsAdmin.isNotEmpty()) {
            "${childrenAdmin.substringBefore(" ")} - CA\n" +
                    "${parentsAdmin.substringBefore(" ")} - P"
        } else {
            ""
        }
    }

    private fun generateExcelEnabled(questionnaire: Questionnaire): Boolean {
        var enabledExcel = false
        questionnaire.childApplication?.answers?.let {
            enabledExcel = it.isNotEmpty()
        }

        questionnaire.parentApplication?.answers?.let {
            enabledExcel = it.isNotEmpty()
        }
        return enabledExcel
    }

    fun getArrayByQuestionnaire(questionnaire: Questionnaire): Array<Questionnaire> {
        return arrayOf(questionnaire.apply {
            childApplication?.familyId = this.familyId
            parentApplication?.familyId = this.familyId
        })
    }

    fun getArgsByQuestionnaire(questionnaire: Questionnaire?): Questionnaire? {
        return questionnaire?.apply {
            childApplication?.familyId = this.familyId
            parentApplication?.familyId = this.familyId
        }
    }
}