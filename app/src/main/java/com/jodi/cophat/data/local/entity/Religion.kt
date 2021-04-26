package com.jodi.cophat.data.local.entity

enum class ReligionType(val religion: String, val religionPoints: Int) {
    CATHOLIC("Católica", 1),
    EVANGELICAL("Evangélica", 2),
    SPIRITIST("Espírita", 3),
    OTHER("Outra", 4),
    NONE("Nenhuma", 5)
}

data class Religion(
    val type: ReligionType,
    val description: String,
    var otherReligion: String? = null
)