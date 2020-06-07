package com.jodi.cophat.feature.questions.adapter

import android.view.View
import com.jodi.cophat.data.presenter.ItemPendingPresenter
import com.jodi.cophat.databinding.ItemPendingBinding
import com.jodi.cophat.feature.questions.adapter.PendingListener
import com.jodi.cophat.ui.BaseViewHolder

class PendingViewHolder(itemView: View, private val pendingListener: PendingListener) :
    BaseViewHolder<ItemPendingBinding, ItemPendingPresenter>(itemView) {

    override fun bind(presenter: ItemPendingPresenter, position: Int) {
        binding?.presenter = presenter

        binding?.ivEditItemPending?.setOnClickListener {
            pendingListener.onEdit(presenter)
        }

        binding?.ivRemoveItemPending?.setOnClickListener {
            pendingListener.onRemove(presenter)
        }
    }
}