package com.jodi.cophat.feature.patient.adapter

import com.jodi.cophat.data.presenter.ItemPatientPresenter

interface PatientListener {

    fun onEdit(item: ItemPatientPresenter)

    fun onRemove(item: ItemPatientPresenter)
}