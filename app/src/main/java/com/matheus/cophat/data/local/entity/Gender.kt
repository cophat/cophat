package com.matheus.cophat.data.local.entity

enum class GenderType(val genderType: String, val genderPoints: Int) {
    FEMALE("Feminino", 1),
    MALE("Masculino", 2)
}

data class Gender (val type: String, val description: String)