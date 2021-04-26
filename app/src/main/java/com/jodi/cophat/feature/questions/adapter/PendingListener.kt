package com.jodi.cophat.feature.questions.adapter

import com.jodi.cophat.data.presenter.ItemPendingPresenter

interface PendingListener {

    fun onEdit(item: ItemPendingPresenter)

    fun onRemove(item: ItemPendingPresenter)
}