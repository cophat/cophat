package com.jodi.cophat.data.presenter

import com.jodi.cophat.data.local.entity.ApplicationEntity

data class PendingPresenter(
    val pendingListVisibility: Int,
    val pendings: List<ItemPendingPresenter>
)