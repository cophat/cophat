package com.matheus.cophat.data.presenter

import android.os.Parcelable
import com.matheus.cophat.data.local.entity.SubQuestion
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubQuestionPresenter(
    val subAnswerPath: String,
    val answerId: Int,
    val subQuestion: SubQuestion,
    val subAnswerId: Int
) : Parcelable