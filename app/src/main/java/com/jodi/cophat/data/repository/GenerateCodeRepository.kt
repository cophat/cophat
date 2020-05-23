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

    suspend fun addChildQuestionnaire(
        application: ApplicationEntity
    ) {
        addChild(
            FirebaseChild.QUESTIONNAIRES,
            Questionnaire(childApplication = application)
        )
    }

    suspend fun addParentQuestionnaire(
        application: ApplicationEntity
    ) {
        addChild(
            FirebaseChild.QUESTIONNAIRES,
            Questionnaire(parentApplication = application)
        )
    }

    // Apagar?
//    suspend fun getPatientName(): String {
//        var list: List<Patient> = getDatabaseChild(FirebaseChild.PATIENTS, Patient::class.java)
//        var index = 0
//        if(list.size.compareTo(0) == 0) {
//            return ""
//        }
//        return list?.get(index)?.name
//    }

//    suspend fun getPatientName(): String? {
//        return dao.getApplication()?.patient?.patientName
//    }
}