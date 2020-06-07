package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.jodi.cophat.data.presenter.ItemPendingPresenter
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
    var status: ApplicationStatus = ApplicationStatus.STARTED,
    @get:Exclude @set:Exclude var keyQuestionnaire: String = "",
    @get:Exclude @set:Exclude var parentPosition: Int = 0,
    @get:Exclude @set:Exclude var typeInterviewee: String = "",
    @get:Exclude @set:Exclude var name: String = ""
) : Parcelable