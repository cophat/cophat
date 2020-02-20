package com.jodi.cophat.data.presenter

import com.jodi.cophat.data.local.entity.Questionnaire

data class QuestionnairePresenter(
    val questionnaire: Questionnaire,
    val questionnaireFirebaseKey: String
)