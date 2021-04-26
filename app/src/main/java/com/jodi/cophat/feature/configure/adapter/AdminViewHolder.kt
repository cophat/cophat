package com.jodi.cophat.feature.configure.adapter

import android.view.View
import com.jodi.cophat.data.presenter.ItemAdminPresenter
import com.jodi.cophat.databinding.ItemAdminBinding
import com.jodi.cophat.ui.BaseViewHolder

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