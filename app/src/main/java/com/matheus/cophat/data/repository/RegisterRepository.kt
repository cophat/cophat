package com.matheus.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.matheus.cophat.data.local.dao.ApplicationDao
import com.matheus.cophat.data.local.entity.ApplicationEntity
import com.matheus.cophat.data.local.entity.Questionnaire
import com.matheus.cophat.data.presenter.QuestionnairePresenter

class RegisterRepository(
    private val database: DatabaseReference,
    private val dao: ApplicationDao
) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    suspend fun getApplication(): ApplicationEntity? {
        return dao.getApplication()
    }

    suspend fun updateApplicationLocally(application: ApplicationEntity) {
        dao.updateApplication(application)
    }

    suspend fun updateParentQuestionnaire(
        application: ApplicationEntity,
        questionnaire: QuestionnairePresenter?
    ) {
        questionnaire?.let {
            questionnaire.questionnaire.parentApplication = application
            updateChild(
                FirebaseChild.QUESTIONNAIRES,
                questionnaire.questionnaireFirebaseKey,
                questionnaire.questionnaire
            )
        }
    }
}