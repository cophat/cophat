package com.jodi.cophat.data.local.dao

import androidx.room.*
import com.jodi.cophat.data.local.entity.ApplicationEntity

@Dao
interface ApplicationDao {

    @Query("SELECT * FROM application")
    suspend fun getAllApplications(): List<ApplicationEntity>

    @Query("SELECT * FROM application LIMIT 1")
    suspend fun getApplication(): ApplicationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(applicationEntity: ApplicationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParentApplication(applicationEntity: MutableList<ApplicationEntity>)

    @Update
    suspend fun updateApplication(applicationEntity: ApplicationEntity)

    @Delete
    suspend fun deleteApplication(applicationEntity: ApplicationEntity)

    @Query("DELETE FROM application")
    suspend fun deleteAllApplications()
}