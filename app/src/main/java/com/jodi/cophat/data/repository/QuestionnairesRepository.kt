package com.jodi.cophat.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.jodi.cophat.data.local.entity.Category
import com.jodi.cophat.data.local.entity.Form
import com.jodi.cophat.data.local.entity.FormType
import com.jodi.cophat.data.local.entity.Question

class QuestionnairesRepository(private val database: DatabaseReference) : BaseRepository() {

    override fun getDatabase(): DatabaseReference {
        return database
    }

    fun getQuery(): Query {
        return database.child(FirebaseChild.QUESTIONNAIRES.pathName)
    }

    suspend fun getCategories(): List<Category> {
        return getDatabaseChild(FirebaseChild.CATEGORIES, Category::class.java)
    }

    suspend fun getForms(formType: FormType): List<Question>? {
        return getDatabaseChild(FirebaseChild.FORMS, Form::class.java)
            .first { it.type == formType }.questions
            ?.map { entry -> entry.value }
            ?.sortedBy { it.id }
    }
}