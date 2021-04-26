package com.jodi.cophat.data.repository

import com.jodi.cophat.data.local.dao.ApplicationDao
import com.jodi.cophat.data.local.entity.ApplicationEntity

class IntroRepository(private val dao: ApplicationDao) {

    suspend fun isEmpty(): Boolean {
        return dao.getAllApplications().isEmpty()
    }

    suspend fun getApplication(): ApplicationEntity? {
        return dao.getApplication()
    }

    suspend fun clearLocally() {
        dao.deleteAllApplications()
    }
}