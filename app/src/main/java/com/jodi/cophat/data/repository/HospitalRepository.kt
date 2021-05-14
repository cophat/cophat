package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.jodi.cophat.data.local.entity.Hospital
import com.jodi.cophat.data.presenter.ItemHospitalPresenter

class HospitalRepository(private val database: DatabaseReference) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    suspend fun getHospitals(): List<ItemHospitalPresenter> {
        val list = ArrayList<ItemHospitalPresenter>()
        getDatabaseChildHash(FirebaseChild.HOSPITALS, Hospital::class.java)
            .forEach { (key, value) ->
                list.add(ItemHospitalPresenter(value.name, value.code, key))
            }
        return list
    }

    suspend fun saveHospital(name: String, code: String) {
        addChild(FirebaseChild.HOSPITALS, Hospital(name, code))
    }

    suspend fun updateHospital(name: String, code: String, key: String) {
        updateChild(FirebaseChild.HOSPITALS, key, Hospital(name, code))
    }

    suspend fun removeHospital(key: String) {
        removeChild(FirebaseChild.HOSPITALS, key)
    }
}