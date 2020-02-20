package com.jodi.cophat.data.presenter

import com.jodi.cophat.data.local.entity.AnswerType
import com.jodi.cophat.data.local.entity.SubAnswerType

data class ItemSubQuestionPresenter(
    var id: Int? = null,
    var type: SubAnswerType? = null,
    var description: String? = null,
    var other: String? = null,
    var alternativeIsEnabled: Boolean = false,
    var descriptionVisibility: Int? = null,
    var otherVisibility: Int? = null,
    var chosenSubAnswer: AnswerType? = null
)