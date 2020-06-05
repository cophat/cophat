package com.jodi.cophat.feature.pending.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityConfigureBinding
import com.jodi.cophat.databinding.ActivityPendingBinding
import com.jodi.cophat.feature.pending.viewmodel.PendingViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PendingActivity : BaseActivity<ActivityPendingBinding>() {

    private val viewModel: PendingViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_pending
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
