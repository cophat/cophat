package com.jodi.cophat.feature.questionnaires.viewmodel

import com.jodi.cophat.data.local.entity.Category
import com.jodi.cophat.data.local.entity.FormType
import com.jodi.cophat.data.local.entity.Question
import com.jodi.cophat.data.repository.QuestionnairesRepository
import com.jodi.cophat.ui.BaseViewModel

class ExportExcelViewModel(private val repository: QuestionnairesRepository) : BaseViewModel() {

    override fun initialize() {}

    suspend fun getCategories() : List<Category> {
        return repository.getCategories()
    }

    suspend fun getQuestions() : List<Question>? {
        return if (isChildren) {
            repository.getForms(FormType.CHILDREN)
        } else {
            repository.getForms(FormType.PARENTS)
        }
    }
}