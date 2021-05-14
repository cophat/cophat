package com.jodi.cophat.feature.hospital.adapter

import android.view.View
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemHospitalPresenter
import com.jodi.cophat.ui.BaseRecyclerView

class HospitalRecyclerAdapter :
    BaseRecyclerView<ItemHospitalPresenter, HospitalViewHolder>() {

    lateinit var hospitalListener: HospitalListener

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_hospital
    }

    override fun getViewHolderInstance(itemView: View, viewType: Int): HospitalViewHolder {
        return HospitalViewHolder(itemView, hospitalListener)
    }

    override fun onBindViewHolder(
        holder: HospitalViewHolder,
        item: ItemHospitalPresenter,
        position: Int
    ) {
        holder.bind(item, position)
    }
}