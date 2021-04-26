package com.jodi.cophat.data.local.entity

enum class SchoolFrequencyType(val schoolFrequency: String, val schoolFrequencyPoints: Int) {
    YES("Sim", 1),
    NO("Não", 0)
}

data class SchoolFrequency(
    val type: SchoolFrequencyType,
    val description: String
)