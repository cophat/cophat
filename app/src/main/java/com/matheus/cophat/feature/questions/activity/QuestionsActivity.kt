package com.matheus.cophat.feature.questions.activity

import com.matheus.cophat.R
import com.matheus.cophat.databinding.ActivityQuestionsBinding
import com.matheus.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.matheus.cophat.ui.BaseActivity
import com.matheus.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionsActivity : BaseActivity<ActivityQuestionsBinding>(){

    private val viewModel: QuestionsViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_questions
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}