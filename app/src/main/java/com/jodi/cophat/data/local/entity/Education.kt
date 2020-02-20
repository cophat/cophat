package com.jodi.cophat.data.local.entity

enum class EducationType(val education: String, val educationPoints: Int) {
    ILLITERATE("Analfabeto / Fundamental I incompleto", 1),
    COMPLETE_FUNDAMENTAL_I("Fundamental I completo / Fundamental II incompleto", 2),
    COMPLETE_FUNDAMENTAL_II("Fundamental II completo / Médio incompleto", 3),
    COMPLETE_MEDIUM("Médio completo / Superior incompleto", 4),
    GRADUATED("Superior completo", 5)
}

data class Education(val type: EducationType, val description: String, val points: Int)