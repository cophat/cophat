package com.jodi.cophat.feature.pending.adapter

import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemPendingPresenter
import com.jodi.cophat.databinding.ItemPendingBinding
import com.jodi.cophat.ui.BaseViewHolder
import com.jodi.cophat.ui.base.view.BottomButtonsListener

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