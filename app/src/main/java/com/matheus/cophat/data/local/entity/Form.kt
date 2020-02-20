package com.matheus.cophat.data.local.entity

enum class FormType(val form: String) {
    PARENTS("Questionário dos Pais"),
    CHILDREN("Questionário das Crianças e Adolescentes")
}

data class Form(
    var type: FormType? = null,
    var description: String? = null,
    var questions: HashMap<String, Question>? = null
)