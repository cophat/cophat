package com.jodi.cophat.feature.intro.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityIntroBinding
import com.jodi.cophat.feature.intro.viewmodel.IntroViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : BaseActivity<ActivityIntroBinding>() {

    private val viewModel: IntroViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_intro
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
