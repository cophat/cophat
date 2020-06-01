package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.jodi.cophat.data.local.dao.ApplicationDao
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.presenter.QuestionnairePresenter

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
        application: MutableList<ApplicationEntity>,
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

    suspend fun updateChildrenQuestionnaire(
        application: ApplicationEntity,
        questionnaire: QuestionnairePresenter?
    ) {
        questionnaire?.let {
            questionnaire.questionnaire.childApplication = application
            updateChild(
                FirebaseChild.QUESTIONNAIRES,
                questionnaire.questionnaireFirebaseKey,
                questionnaire.questionnaire
            )
        }
    }
}