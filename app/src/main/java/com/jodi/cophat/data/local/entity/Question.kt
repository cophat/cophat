package com.jodi.cophat.data.local.entity

data class Question(
    var id: Int? = null,
    var category: CategoryType? = null,
    var statement: String? = null,
    var statementMale: String? = null,
    var statementFemale: String? = null,
    var subQuestions: HashMap<String, SubQuestion>? = null
)