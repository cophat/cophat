package com.jodi.cophat.feature.questionnaires.adapter

import com.jodi.cophat.data.local.entity.Questionnaire

interface QuestionnaireListener {

    fun onClickExcel(item: Questionnaire)
}