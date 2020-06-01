package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.jodi.cophat.data.local.entity.Patient
import com.jodi.cophat.data.presenter.ItemPatientPresenter

class PatientRepository(private val database: DatabaseReference) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database

    }

    suspend fun getAllPatients(): List<Patient> {
        val list = ArrayList<Patient>()
        getDatabaseChildHash(FirebaseChild.PATIENTS, Patient::class.java)
            .forEach { (key, value) ->
                list.add(Patient(value.intervieweeName, value.relationship,
                    value.motherProfession, value.fatherProfession, value.maritalStatus,
                    value.religion, value.name, value.medicalRecords, value.identifyCode,
                    value.birthday, value.age, value.gender, value.diagnosis,
                    value.diagnosticTime, value.internedDays, value.hospitalizations,
                    value.schooling, value.schoolFrequency, value.liveInThisCity, value.address,
                    value.monthlyIncome, value.educationDegree, value.admin, value.hospital))
            }
        return list
    }

    suspend fun getPatients(): List<ItemPatientPresenter> {
        val list = ArrayList<ItemPatientPresenter>()
        getDatabaseChildHash(FirebaseChild.PATIENTS, Patient::class.java)
            .forEach { (key, value) ->
                list.add(ItemPatientPresenter(value.intervieweeName, value.relationship,
                    value.motherProfession, value.fatherProfession, value.maritalStatus,
                    value.religion, value.name, value.medicalRecords, value.identifyCode,
                    value.birthday, value.age, value.gender, value.diagnosis,
                    value.diagnosticTime, value.internedDays, value.hospitalizations,
                    value.schooling, value.schoolFrequency, value.liveInThisCity, value.address,
                    value.monthlyIncome, value.educationDegree, value.admin, value.hospital, key))
            }
        return list
    }

    suspend fun savePatient(intervieweeName: String ,relationship: String,motherProfession: String ,fatherProfession: String,maritalStatus: String,
                            religion: String,name: String, medicalRecords: String,identifyCode: String ,birthday: String ,age: Int,gender: String,
                            diagnosis: String,diagnosticTime: Int,internedDays: Int,hospitalizations: Int,schooling: String,schoolFrequency: String,
                            liveInThisCity: String,address: String,monthlyIncome: String, educationDegree: String, hospital: String, admin: String) {
        addChild(FirebaseChild.PATIENTS, Patient(intervieweeName, relationship, motherProfession,fatherProfession,
            maritalStatus, religion, name, medicalRecords, identifyCode, birthday, age, gender, diagnosis, diagnosticTime,
            internedDays, hospitalizations, schooling, schoolFrequency, liveInThisCity, address, monthlyIncome, educationDegree, hospital, admin))
    }

    suspend fun updatePatient(intervieweeName: String ,relationship: String,motherProfession: String ,fatherProfession: String,maritalStatus: String,
                              religion: String,name: String, medicalRecords: String,identifyCode: String ,birthday: String ,age: Int,gender: String,
                              diagnosis: String,diagnosticTime: Int,internedDays: Int,hospitalizations: Int,schooling: String,schoolFrequency: String,
                              liveInThisCity: String,address: String,monthlyIncome: String, educationDegree: String, hospital: String, admin: String, key: String) {
        updateChild(FirebaseChild.PATIENTS, key, Patient(intervieweeName, relationship, motherProfession,fatherProfession,
            maritalStatus, religion, name, medicalRecords, identifyCode, birthday, age, gender, diagnosis, diagnosticTime,
            internedDays, hospitalizations, schooling, schoolFrequency, liveInThisCity, address, monthlyIncome, educationDegree, hospital, admin))
    }

    suspend fun removePatient(key: String) {
        removeChild(FirebaseChild.PATIENTS, key)
    }
}