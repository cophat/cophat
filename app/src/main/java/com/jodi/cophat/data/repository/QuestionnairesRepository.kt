package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.jodi.cophat.data.local.entity.*

class QuestionnairesRepository(private val database: DatabaseReference) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    fun getQuery(): Query {
        database.keepSynced(true)
        return database.child(FirebaseChild.QUESTIONNAIRES.pathName)
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
            .forEach { (key, value) ->
                list.add(Questionnaire(value.identifyCode, value.childApplication,
                    value.parentApplication))
            }

        val listPatients = ArrayList<Patient>()
        getDatabaseChildHash(FirebaseChild.PATIENTS, Patient::class.java)
            .forEach { (key, value) ->
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

}