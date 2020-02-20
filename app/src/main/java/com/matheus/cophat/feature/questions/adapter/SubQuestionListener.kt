package com.matheus.cophat.feature.questions.adapter

import com.matheus.cophat.data.presenter.ItemSubQuestionPresenter

interface SubQuestionListener {

    fun onSubAnswerChanged(item: ItemSubQuestionPresenter)

    fun onSubAnswerOtherChanged(item: ItemSubQuestionPresenter)
}
