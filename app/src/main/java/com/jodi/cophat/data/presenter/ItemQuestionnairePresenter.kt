package com.jodi.cophat.data.presenter

import androidx.annotation.DrawableRes
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.Patient
import com.jodi.cophat.data.local.entity.Questionnaire
import com.jodi.cophat.data.local.entity.QuestionnaireReport

data class ItemQuestionnairePresenter(
    val applicationId: String,
    val parentInformation: String,
    @DrawableRes
    val childrenDrawable: Int,
    val childrenState: String,
    val parentsState: String,
    val applicationsTime: String,
    val hospital: String,
    val admin: String,
    val excelEnabled: Boolean,
    val questionnaireReport: QuestionnaireReport
)