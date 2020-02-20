package com.matheus.cophat.feature.questionnaires.viewmodel

import com.matheus.cophat.data.local.entity.Category
import com.matheus.cophat.data.local.entity.FormType
import com.matheus.cophat.data.local.entity.Question
import com.matheus.cophat.data.repository.QuestionnairesRepository
import com.matheus.cophat.ui.BaseViewModel

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