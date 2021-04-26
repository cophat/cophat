package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.Inet4Address

@Parcelize
data class Patient(
    var intervieweeName: String = "",
    var relationship: String = "",
    var motherProfession: String = "",
    var fatherProfession: String = "",
    var maritalStatus: String = "",
    var religion: String = "",
    var name: String = "",
    var medicalRecords: String = "",
    var identificationCode: String = "",
    var birthday: String = "",
    var age: Int = 0,
    var gender: String = "",
    var diagnosis: String = "",
    var diagnosticTime: Int = 0,
    var internedDays: Int = 0,
    var hospitalizations: Int = 0,
    var schooling: String = "",
    var schoolFrequency: String = "",
    var liveInThisCity: String = "",
    var address: String = "",
    var monthlyIncome: String = "",
    var educationDegree: String = "",
    var hospital: String = "",
    var admin: String = ""
) : Parcelable