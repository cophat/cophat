package com.jodi.cophat.feature.patient.adapter

import android.view.View
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemPatientPresenter
import com.jodi.cophat.feature.patient.adapter.PatientViewHolder
import com.jodi.cophat.ui.BaseRecyclerView

class PatientRecyclerAdapter :
    BaseRecyclerView<ItemPatientPresenter, PatientViewHolder>() {

    lateinit var patientListener: PatientListener

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_patient
    }

    override fun getViewHolderInstance(itemView: View, viewType: Int): PatientViewHolder {
        return PatientViewHolder(itemView, patientListener)
    }

    override fun onBindViewHolder(
        holder: PatientViewHolder,
        item: ItemPatientPresenter,
        position: Int
    ) {
        holder.bind(item, position)
    }
}