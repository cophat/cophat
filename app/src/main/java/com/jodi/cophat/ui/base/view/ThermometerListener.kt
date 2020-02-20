package com.jodi.cophat.ui.base.view

import com.jodi.cophat.data.local.entity.AnswerType

interface ThermometerListener {

    fun onAnswerChanged(answerType: AnswerType)
}