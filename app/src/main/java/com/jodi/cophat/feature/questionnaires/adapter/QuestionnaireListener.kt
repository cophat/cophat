package com.jodi.cophat.feature.questionnaires.adapter

import com.jodi.cophat.data.local.entity.QuestionnaireReport

interface QuestionnaireListener {

    fun onClickExcel(item: QuestionnaireReport)
}