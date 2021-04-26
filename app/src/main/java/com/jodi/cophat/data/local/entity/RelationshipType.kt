package com.jodi.cophat.data.local.entity

enum class RelationshipType(val relationship: String, val relationshipTypePoints: Int) {
    FATHER("Pai", 1),
    MOTHER("Mãe", 2),
    GRAND_FATHER_M("Avô materno", 3),
    GRAND_MOTHER_M("Avó materna", 4),
    GRAND_FATHER_P("Avô paterno", 5),
    GRAND_MOTHER_P("Avó materna", 6),
    OTHER("Outro", 7),
}

data class RelationshipTypeS(val type: RelationshipType, val description: String, val points: Int)