package com.matheus.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.matheus.cophat.data.local.entity.Admin
import com.matheus.cophat.data.local.entity.Form
import com.matheus.cophat.data.presenter.ItemAdminPresenter

class ConfigureRepository(private val database: DatabaseReference) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    suspend fun getAdmins(): List<ItemAdminPresenter> {
        val list = ArrayList<ItemAdminPresenter>()
        getDatabaseChildHash(FirebaseChild.ADMINS, Admin::class.java)
            .forEach { (key, value) ->
                list.add(ItemAdminPresenter(value.name, value.contact, key))
            }
        return list
    }

    suspend fun saveAdmin(name: String, contact: String) {
        addChild(FirebaseChild.ADMINS, Admin(name, contact))
    }

    suspend fun updateAdmin(name: String, contact: String, key: String) {
        updateChild(FirebaseChild.ADMINS, key, Admin(name, contact))
    }

    suspend fun removeAdmin(key: String) {
        removeChild(FirebaseChild.ADMINS, key)
    }
}