package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.jodi.cophat.data.local.dao.ApplicationDao
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.data.presenter.QuestionnairePresenter

class QuestionsRepository(
    private val database: DatabaseReference,
    private val dao: ApplicationDao
) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    suspend fun getForms(formType: FormType): List<Question>? {
        return getDatabaseChild(FirebaseChild.FORMS, Form::class.java)
            .first { it.type == formType }.questions
            ?.map { entry -> entry.value }
            ?.sortedBy { it.id }
    }

    suspend fun getFamilyId(): String? {
        return dao.getApplication()?.identifyCode
    }

    suspend fun updateParentQuestionnaire(questionnaire: QuestionnairePresenter?) {
        questionnaire?.let {
            updateChild(
                FirebaseChild.QUESTIONNAIRES,
                questionnaire.questionnaireFirebaseKey,
                questionnaire.questionnaire
            )
        }
    }

    suspend fun updateChildrenQuestionnaire(questionnaire: QuestionnairePresenter?) {
        questionnaire?.let {
            updateChild(
                FirebaseChild.QUESTIONNAIRES,
                questionnaire.questionnaireFirebaseKey,
                questionnaire.questionnaire
            )
        }
    }

    suspend fun clearLocally() {
        dao.deleteAllApplications()
    }
}