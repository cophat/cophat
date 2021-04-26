package com.jodi.cophat.feature.configure.adapter

import com.jodi.cophat.data.presenter.ItemAdminPresenter

interface AdminListener {

    fun onEdit(item: ItemAdminPresenter)

    fun onRemove(item: ItemAdminPresenter)
}