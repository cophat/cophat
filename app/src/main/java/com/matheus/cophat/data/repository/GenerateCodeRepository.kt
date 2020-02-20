package com.matheus.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.matheus.cophat.data.local.dao.ApplicationDao
import com.matheus.cophat.data.local.entity.Admin
import com.matheus.cophat.data.local.entity.ApplicationEntity
import com.matheus.cophat.data.local.entity.Hospital
import com.matheus.cophat.data.local.entity.Questionnaire

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

    suspend fun addChildQuestionnaire(
        familyId: String,
        hospital: String,
        application: ApplicationEntity
    ) {
        addChild(
            FirebaseChild.QUESTIONNAIRES,
            Questionnaire(familyId, hospital, childApplication = application)
        )
    }

    suspend fun addParentQuestionnaire(
        familyId: String,
        hospital: String,
        application: ApplicationEntity
    ) {
        addChild(
            FirebaseChild.QUESTIONNAIRES,
            Questionnaire(familyId, hospital, parentApplication = application)
        )
    }

    suspend fun getPatientName(): String? {
        return dao.getApplication()?.patient?.patientName
    }
}