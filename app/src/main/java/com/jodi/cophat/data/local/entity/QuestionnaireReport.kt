package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionnaireReport(
    var identificationCode: String? = "",
    var patient: Patient? = null,
    var childApplication: ApplicationEntity? = null,
    var parentApplication: ApplicationEntity? = null,
    var questionnaireDividerVisibility: Int = 0
) : Parcelable