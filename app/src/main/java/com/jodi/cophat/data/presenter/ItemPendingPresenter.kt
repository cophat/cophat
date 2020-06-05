package com.jodi.cophat.data.presenter

data class ItemPendingPresenter(
    var keyQuestionnaire: String = "",
    var parentPosition: Int = 0,
    var typeInterviewee: String = "",
    var identifyCode: String = "",
    var name: String = "",
    var admin: String = "",
    var hospital: String = "",
    var date: String = "",
    var pendingDividerVisibility: Int = 0
)