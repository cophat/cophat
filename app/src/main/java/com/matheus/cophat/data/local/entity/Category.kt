package com.matheus.cophat.data.local.entity

enum class CategoryType(val category: String) {
    UNDERSTANDING_DISEASE("Compreensão doença"),
    HOSPITALIZATION("Internação"),
    TREATMENT_SUCCESS("Realização e êxito do tratamento"),
    COLLATERAL_EFFECTS("Efeitos colaterais"),
    SCHOOL_EXPECTATION("Expectativa do retorno à escola")
}

data class Category(var type: CategoryType? = null, var description: String? = null)