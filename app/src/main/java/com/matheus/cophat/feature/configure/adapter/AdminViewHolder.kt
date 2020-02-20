package com.matheus.cophat.feature.configure.adapter

import android.view.View
import com.matheus.cophat.data.presenter.ItemAdminPresenter
import com.matheus.cophat.databinding.ItemAdminBinding
import com.matheus.cophat.ui.BaseViewHolder

class AdminViewHolder(itemView: View, private val adminListener: AdminListener) :
    BaseViewHolder<ItemAdminBinding, ItemAdminPresenter>(itemView) {

    override fun bind(presenter: ItemAdminPresenter, position: Int) {
        binding?.presenter = presenter

        binding?.ivEditItemAdmin?.setOnClickListener {
            adminListener.onEdit(presenter)
        }

        binding?.ivRemoveItemAdmin?.setOnClickListener {
            adminListener.onRemove(presenter)
        }
    }
}