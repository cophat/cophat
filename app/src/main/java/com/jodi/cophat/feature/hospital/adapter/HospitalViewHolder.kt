package com.jodi.cophat.feature.hospital.adapter

import android.view.View
import com.jodi.cophat.data.presenter.ItemHospitalPresenter
import com.jodi.cophat.databinding.ItemHospitalBinding
import com.jodi.cophat.ui.BaseViewHolder

class HospitalViewHolder(itemView: View, private val HospitalListener: HospitalListener) :
    BaseViewHolder<ItemHospitalBinding, ItemHospitalPresenter>(itemView) {

    override fun bind(presenter: ItemHospitalPresenter, position: Int) {
        binding?.presenter = presenter

        binding?.ivEditItemHospital?.setOnClickListener {
            HospitalListener.onEdit(presenter)
        }

        binding?.ivRemoveItemHospital?.setOnClickListener {
            HospitalListener.onRemove(presenter)
        }
    }
}