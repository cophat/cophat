package com.jodi.cophat.data.presenter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemPendingPresenter(
    var keyQuestionnaire: String = "",
    var parentPosition: Int = 0,
    var typeInterviewee: String = "",
    var identifyCode: String = "",
    var name: String? = null,
    var admin: String? = null,
    var hospital: String? = null,
    var date: String? = null,
    var pendingDividerVisibility: Int = 0
): Parcelable