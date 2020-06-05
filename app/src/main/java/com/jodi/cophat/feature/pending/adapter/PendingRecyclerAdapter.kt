package com.jodi.cophat.feature.pending.adapter

import android.view.View
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemPendingPresenter
import com.jodi.cophat.ui.BaseRecyclerView

class PendingRecyclerAdapter :
    BaseRecyclerView<ItemPendingPresenter, PendingViewHolder>() {

    lateinit var pendingListener: PendingListener

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_pending
    }

    override fun getViewHolderInstance(itemView: View, viewType: Int): PendingViewHolder {
        return PendingViewHolder(itemView, pendingListener)
    }

    override fun onBindViewHolder(
        holder: PendingViewHolder,
        item: ItemPendingPresenter,
        position: Int
    ) {
        holder.bind(item, position)
    }
}