package com.matheus.cophat.feature.questionnaires.activity

import com.matheus.cophat.R
import com.matheus.cophat.databinding.ActivityQuestionnairesBinding
import com.matheus.cophat.feature.questionnaires.viewmodel.QuestionnairesViewModel
import com.matheus.cophat.ui.BaseActivity
import com.matheus.cophat.ui.BaseViewModel
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
