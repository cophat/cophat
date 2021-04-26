package com.jodi.cophat.feature.questionnaires.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityQuestionnairesBinding
import com.jodi.cophat.feature.questionnaires.viewmodel.QuestionnairesViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionnairesActivity : BaseActivity<ActivityQuestionnairesBinding>() {

    private val viewModel: QuestionnairesViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_questionnaires
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
