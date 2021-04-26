package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.jodi.cophat.data.local.dao.ApplicationDao
import com.jodi.cophat.data.local.entity.*

class GenerateCodeRepository(
    private val database: DatabaseReference,
    private val dao: ApplicationDao
) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    suspend fun getHospitals(): List<Hospital> {
        return getDatabaseChild(FirebaseChild.HOSPITALS, Hospital::class.java)
    }

    suspend fun getAdmins(): List<Admin> {
        return getDatabaseChild(FirebaseChild.ADMINS, Admin::class.java)
    }

    suspend fun saveApplicationLocally(application: ApplicationEntity) {
        dao.insertApplication(application)
    }

    suspend fun saveParentApplicationLocally(application: MutableList<ApplicationEntity>) {
        dao.insertParentApplication(application)
    }

    suspend fun addChildQuestionnaire(
        identificationCode: String,
        application: ApplicationEntity
    ) {
        addChild(
            FirebaseChild.QUESTIONNAIRES,
            Questionnaire(identificationCode, childApplication = application)
        )
    }

    suspend fun addParentQuestionnaire(
        identificationCode: String,
        application: MutableList<ApplicationEntity>
    ) {
        addChild(
            FirebaseChild.QUESTIONNAIRES,
            Questionnaire(identificationCode, parentApplication = application)
        )
    }
}