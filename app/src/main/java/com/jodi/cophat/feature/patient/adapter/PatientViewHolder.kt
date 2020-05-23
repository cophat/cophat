package com.jodi.cophat.feature.patient.adapter

import android.view.View
import com.jodi.cophat.data.presenter.ItemPatientPresenter
import com.jodi.cophat.databinding.ItemPatientBinding
import com.jodi.cophat.ui.BaseViewHolder

class PatientViewHolder(itemView: View, private val patientListener: PatientListener) :
    BaseViewHolder<ItemPatientBinding, ItemPatientPresenter>(itemView) {

    override fun bind(presenter: ItemPatientPresenter, position: Int) {
        binding?.presenter = presenter

        binding?.ivEditItemPatient?.setOnClickListener {
            patientListener.onEdit(presenter)
        }

        binding?.ivRemoveItemPatient?.setOnClickListener {
            patientListener.onRemove(presenter)
        }
    }
}