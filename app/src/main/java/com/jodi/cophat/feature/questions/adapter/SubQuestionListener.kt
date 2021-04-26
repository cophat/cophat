package com.jodi.cophat.feature.questions.adapter

import com.jodi.cophat.data.presenter.ItemSubQuestionPresenter

interface SubQuestionListener {

    fun onSubAnswerChanged(item: ItemSubQuestionPresenter)

    fun onSubAnswerOtherChanged(item: ItemSubQuestionPresenter)
}
