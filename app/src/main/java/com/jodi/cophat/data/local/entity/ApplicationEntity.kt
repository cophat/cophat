package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
@Entity(tableName = "application")
data class ApplicationEntity(
    @PrimaryKey(autoGenerate = true)
    @get:Exclude @set:Exclude var id: Int? = null,
    var identifyCode: String? = "",
    var hospital: String? = null,
    var intervieweeName: String? = null,
    var gender: String? = null,
    var relationship: String? = null,
    var admin: String? = null,
    var answers: HashMap<String, Answer>? = null,
    var date: String? = null,
    var startHour: Long? = null,
    var endHour: Long? = null,
    var status: ApplicationStatus = ApplicationStatus.STARTED
) : Parcelable