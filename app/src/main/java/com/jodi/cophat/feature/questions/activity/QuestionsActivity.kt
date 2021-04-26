package com.jodi.cophat.feature.questions.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityQuestionsBinding
import com.jodi.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
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