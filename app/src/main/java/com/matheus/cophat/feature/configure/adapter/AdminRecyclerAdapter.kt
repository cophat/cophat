package com.matheus.cophat.feature.configure.adapter

import android.view.View
import com.matheus.cophat.R
import com.matheus.cophat.data.presenter.ItemAdminPresenter
import com.matheus.cophat.ui.BaseRecyclerView

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