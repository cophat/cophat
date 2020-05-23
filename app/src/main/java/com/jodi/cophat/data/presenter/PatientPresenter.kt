package com.jodi.cophat.data.presenter

data class PatientPresenter(
    val patientListVisibility: Int,
    val patients: List<ItemPatientPresenter>
)