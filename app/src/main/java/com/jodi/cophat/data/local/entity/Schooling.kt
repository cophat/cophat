package com.jodi.cophat.data.local.entity

enum class SchoolingType(val schooling: String, val schoolingPoints: Int) {
    PRE("Pré", 1),
    FIRST_YEAR("1º ano", 2),
    SECOND_YEAR("2º ano", 3),
    THIRD_YEAR("3º ano", 4),
    FOURTH_YEAR("4º ano", 5),
    FIFTH_YEAR("5º ano", 6),
    SIXTH_YEAR("6º ano", 7),
    SEVENTH_YEAR("7º ano", 8),
    EIGHTH_YEAR("8º ano", 9),
    NINTH_YEAR("9º ano", 10)
}

data class Schooling(val type: SchoolingType, val description: String)