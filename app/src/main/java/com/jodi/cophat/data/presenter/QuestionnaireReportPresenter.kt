package com.jodi.cophat.data.presenter

import com.jodi.cophat.data.local.entity.QuestionnaireReport

data class QuestionnaireReportPresenter(
    val questionnaireReportListVisibility: Int,
    val questionnaireReports: List<QuestionnaireReport>
)
