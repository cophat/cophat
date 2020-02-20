package com.matheus.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Patient(
    var motherProfession: String? = null,
    var fatherProfession: String? = null,
    var maritalStatus: String? = null,
    var religion: String? = null,
    var patientName: String? = null,
    var medicalRecords: String? = null,
    var birthday: String? = null,
    var age: Int? = null,
    var gender: String? = null,
    var diagnosis: String? = null,
    var diagnosticTime: String? = null,
    var internedDays: Int? = null,
    var hospitalizations: Int? = null,
    var schooling: String? = null,
    var schoolFrequency: Boolean? = null,
    var liveInThisCity: Boolean? = null,
    var home: String? = null,
    var monthlyIncome: String? = null,
    var educationDegree: String? = null
) : Parcelable