package com.jodi.cophat.feature.hospital.adapter

import com.jodi.cophat.data.presenter.ItemHospitalPresenter

interface HospitalListener {

    fun onEdit(item: ItemHospitalPresenter)

    fun onRemove(item: ItemHospitalPresenter)
}