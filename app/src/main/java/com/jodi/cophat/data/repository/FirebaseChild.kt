package com.jodi.cophat.data.repository

enum class FirebaseChild(val pathName: String) {
    HOSPITALS("hospitals"),
    ADMINS("admins"),
    PATIENTS("patients"),
    QUESTIONNAIRES("questionnaires"),
    CATEGORIES("categories"),
    FORMS("forms")
}