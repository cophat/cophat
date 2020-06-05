package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.data.presenter.ItemPendingPresenter


class QuestionnairesRepository(
    private val database: DatabaseReference
) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    suspend fun getCategories(): List<Category> {
        return getDatabaseChild(FirebaseChild.CATEGORIES, Category::class.java)
    }

    suspend fun getForms(formType: FormType): List<Question>? {
        return getDatabaseChild(FirebaseChild.FORMS, Form::class.java)
            .first { it.type == formType }.questions
            ?.map { entry -> entry.value }
            ?.sortedBy { it.id }
    }

    suspend fun getQuestionnaires(): List<QuestionnaireReport> {
        val list = ArrayList<Questionnaire>()
        getDatabaseChildHash(FirebaseChild.QUESTIONNAIRES, Questionnaire::class.java)
            .forEach { (_, value) ->
                list.add(Questionnaire(value.identifyCode, value.childApplication,
                    value.parentApplication))
            }

        val listPatients = ArrayList<Patient>()
        getDatabaseChildHash(FirebaseChild.PATIENTS, Patient::class.java)
            .forEach { (_, value) ->
                listPatients.add(
                    Patient(value.intervieweeName, value.relationship,
                    value.motherProfession, value.fatherProfession, value.maritalStatus,
                    value.religion, value.name, value.medicalRecords, value.identifyCode,
                    value.birthday, value.age, value.gender, value.diagnosis,
                    value.diagnosticTime, value.internedDays, value.hospitalizations,
                    value.schooling, value.schoolFrequency, value.liveInThisCity, value.address,
                    value.monthlyIncome, value.educationDegree, value.admin, value.hospital)
                )
            }

        var listQuestionnaireReport = ArrayList<QuestionnaireReport>()
        list.map {q ->
            var patient: List<Patient?> = listPatients.filter { it.identifyCode == q.identifyCode}
            if (q.childApplication != null && q.childApplication?.status == ApplicationStatus.COMPLETED && !q.parentApplication.isEmpty() && !patient.isEmpty()) {
                q.parentApplication.map { pa ->
                    if(pa.status == ApplicationStatus.COMPLETED){
                        listQuestionnaireReport.add(
                            QuestionnaireReport(
                                pa.identifyCode,
                                patient.last(),
                                q.childApplication,
                                pa
                            )
                        )
                    }
                }

            }
        }

        return listQuestionnaireReport
    }

    suspend fun getPendingQuestionnaires(): List<ItemPendingPresenter> {
        val allQuestionnaires = ArrayList<Questionnaire>()
        getDatabaseChildHash(FirebaseChild.QUESTIONNAIRES, Questionnaire::class.java)
            .forEach { (key, value) ->
                allQuestionnaires.add(Questionnaire(value.identifyCode, value.childApplication,
                    value.parentApplication, key))
            }

        val listPatients = ArrayList<Patient>()
        getDatabaseChildHash(FirebaseChild.PATIENTS, Patient::class.java)
            .forEach { (_, value) ->
                listPatients.add(
                    Patient(value.intervieweeName, value.relationship,
                        value.motherProfession, value.fatherProfession, value.maritalStatus,
                        value.religion, value.name, value.medicalRecords, value.identifyCode,
                        value.birthday, value.age, value.gender, value.diagnosis,
                        value.diagnosticTime, value.internedDays, value.hospitalizations,
                        value.schooling, value.schoolFrequency, value.liveInThisCity, value.address,
                        value.monthlyIncome, value.educationDegree, value.admin, value.hospital)
                )
            }

        val applicationsPeding = ArrayList<ItemPendingPresenter>()
        allQuestionnaires.map {q ->
            if(q.childApplication != null && q.childApplication?.status?.equals(ApplicationStatus.STARTED)!!){
                var patient: String = listPatients.filter { it.identifyCode == q.identifyCode}.last().name
                applicationsPeding.add(ItemPendingPresenter(
                    identifyCode = q.identifyCode,
                    name = patient,
                    typeInterviewee = "Paciente",
                    admin = q.childApplication?.admin!!,
                    hospital = q.childApplication?.hospital!!,
                    date = q.childApplication?.date!!,
                    keyQuestionnaire = q.key

                ))
            }
            q.parentApplication.mapIndexed { index, app ->
                if(app.status.equals(ApplicationStatus.STARTED)){
                    applicationsPeding.add(ItemPendingPresenter(
                        identifyCode = q.identifyCode,
                        name = app.intervieweeName!!,
                        typeInterviewee = "Responsável - ${app.relationship}",
                        admin = app.admin!!,
                        hospital = app.hospital!!,
                        date = app.date!!,
                        parentPosition = index,
                        keyQuestionnaire = q.key

                    ))
                }
            }
        }
        return applicationsPeding
    }

    suspend fun removePendingChild(key: String) {
        removeChildOnQuestionnaire(FirebaseChild.QUESTIONNAIRES, key)
    }

    suspend fun removePendingParent(key: String, position: Int){
        removeParentOnQuestionnaire(FirebaseChild.QUESTIONNAIRES, key, position)
    }


}