package com.matheus.cophat.data.repository

enum class FirebaseChild(val pathName: String) {
    HOSPITALS("hospitals"),
    ADMINS("admins"),
    QUESTIONNAIRES("questionnaires"),
    CATEGORIES("categories"),
    FORMS("forms")
}