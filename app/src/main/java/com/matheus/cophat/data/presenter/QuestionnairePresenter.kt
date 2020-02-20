package com.matheus.cophat.data.presenter

import com.matheus.cophat.data.local.entity.Questionnaire

data class QuestionnairePresenter(
    val questionnaire: Questionnaire,
    val questionnaireFirebaseKey: String
)