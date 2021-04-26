package com.jodi.cophat.feature.generate.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityGenerateCodeBinding
import com.jodi.cophat.feature.generate.viewmodel.GenerateCodeViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
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
