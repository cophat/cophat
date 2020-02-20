package com.matheus.cophat.data.presenter

import androidx.annotation.DrawableRes
import com.matheus.cophat.data.local.entity.Questionnaire

data class ItemQuestionnairePresenter(
    val applicationId: String,
    @DrawableRes
    val childrenDrawable: Int,
    val childrenState: String,
    val parentsState: String,
    val applicationsTime: String,
    val hospital: String,
    val admin: String,
    val excelEnabled: Boolean,
    val questionnaire: Questionnaire
)