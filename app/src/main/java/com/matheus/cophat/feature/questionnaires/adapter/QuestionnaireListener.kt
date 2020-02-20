package com.matheus.cophat.feature.questionnaires.adapter

import com.matheus.cophat.data.local.entity.Questionnaire

interface QuestionnaireListener {

    fun onClickExcel(item: Questionnaire)
}