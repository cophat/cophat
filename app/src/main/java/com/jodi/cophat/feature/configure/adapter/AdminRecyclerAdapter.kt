package com.jodi.cophat.feature.configure.adapter

import android.view.View
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemAdminPresenter
import com.jodi.cophat.ui.BaseRecyclerView

class AdminRecyclerAdapter :
    BaseRecyclerView<ItemAdminPresenter, AdminViewHolder>() {

    lateinit var adminListener: AdminListener

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_admin
    }

    override fun getViewHolderInstance(itemView: View, viewType: Int): AdminViewHolder {
        return AdminViewHolder(itemView, adminListener)
    }

    override fun onBindViewHolder(
        holder: AdminViewHolder,
        item: ItemAdminPresenter,
        position: Int
    ) {
        holder.bind(item, position)
    }
}