package com.jodi.cophat.data.local.entity

enum class MaritalStatusType(val maritalStatus: String, val maritalStatusPoints: Int) {
    MARRIED("Casado", 1),
    AMASSED("Amasiado", 2),
    DIVORCED_SEPARATED("Divorciado/Separado", 3),
    SINGLE("Solteiro", 4),
    WIDOWER("Viúvo", 5)
}

data class MaritalStatus(val type: MaritalStatusType, val description: String, val points: Int)