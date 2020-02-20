package com.matheus.cophat.ui.base.view

import com.matheus.cophat.data.local.entity.AnswerType

interface ThermometerListener {

    fun onAnswerChanged(answerType: AnswerType)
}