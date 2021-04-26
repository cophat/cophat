package com.jodi.cophat.feature.configure.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityConfigureBinding
import com.jodi.cophat.feature.configure.viewmodel.ConfigureViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfigureActivity : BaseActivity<ActivityConfigureBinding>() {

    private val viewModel: ConfigureViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_configure
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
