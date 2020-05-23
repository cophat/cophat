package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Questionnaire(
    var childApplication: ApplicationEntity? = null,
    var parentApplication: ApplicationEntity? = null
) : Parcelable