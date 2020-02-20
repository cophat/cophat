package com.matheus.cophat.feature.generate.activity

import com.matheus.cophat.R
import com.matheus.cophat.databinding.ActivityGenerateCodeBinding
import com.matheus.cophat.feature.generate.viewmodel.GenerateCodeViewModel
import com.matheus.cophat.ui.BaseActivity
import com.matheus.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateCodeActivity : BaseActivity<ActivityGenerateCodeBinding>() {

    private val viewModel: GenerateCodeViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_generate_code
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
