package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Questionnaire(
    var identifyCode: String = "",
    var childApplication: ApplicationEntity? = null,
    var parentApplication: MutableList<ApplicationEntity> = ArrayList<ApplicationEntity>(),
    var key: String = ""
) : Parcelable