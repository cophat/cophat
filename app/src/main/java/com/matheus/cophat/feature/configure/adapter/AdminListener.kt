package com.matheus.cophat.feature.configure.adapter

import com.matheus.cophat.data.presenter.ItemAdminPresenter

interface AdminListener {

    fun onEdit(item: ItemAdminPresenter)

    fun onRemove(item: ItemAdminPresenter)
}