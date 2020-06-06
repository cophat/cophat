package com.jodi.cophat.feature.pending.adapter

import com.jodi.cophat.data.presenter.ItemPendingPresenter

interface PendingListener {

    fun onEdit(item: ItemPendingPresenter)

    fun onRemove(item: ItemPendingPresenter)
}