package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Questionnaire(
    var identifyCode: String = "",
    var childApplication: ApplicationEntity? = null,
    var parentApplication: MutableList<ApplicationEntity> = ArrayList<ApplicationEntity>(),
    @set:Exclude @get:Exclude var key: String? = null
) : Parcelable