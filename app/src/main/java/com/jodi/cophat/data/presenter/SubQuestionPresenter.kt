package com.jodi.cophat.data.presenter

import android.os.Parcelable
import com.jodi.cophat.data.local.entity.SubQuestion
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubQuestionPresenter(
    val subAnswerPath: String,
    val answerId: Int,
    val subQuestion: SubQuestion,
    val subAnswerId: Int
) : Parcelable