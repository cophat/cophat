package com.jodi.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class AnswerType(val chosenAnswer: String, val chosenAnswerPoints: Int) {
    NEVER("Nunca", 0),
    ALMOST_NEVER("Quase nunca", 1),
    SOMETIMES("Às vezes", 2),
    OFTEN("Quase sempre", 3),
    ALWAYS("Sempre", 4),
    NO("Não", 0),
    YES("Sim", 1)
}

@Parcelize
data class Answer(
    var id: Int = 0,
    var chosenAnswer: AnswerType? = null,
    var subAnswers: HashMap<String, SubAnswer>? = null
) : Parcelable