package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubQuestion(
    var id: Int? = null,
    var statement: String? = null,
    var statementMale: String? = null,
    var statementFemale: String? = null,
    var alternatives: HashMap<String, Alternative>? = null
) : Parcelable