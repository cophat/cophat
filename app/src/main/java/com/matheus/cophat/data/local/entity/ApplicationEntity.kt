package com.matheus.cophat.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
@Entity(tableName = "application")
data class ApplicationEntity(
    @PrimaryKey
    @get:Exclude var familyId: String = "",
    var patient: Patient? = null,
    var admin: String? = null,
    var answers: HashMap<String, Answer>? = null,
    var date: String? = null,
    var startHour: Long? = null,
    var endHour: Long? = null,
    var status: ApplicationStatus = ApplicationStatus.STARTED
) : Parcelable