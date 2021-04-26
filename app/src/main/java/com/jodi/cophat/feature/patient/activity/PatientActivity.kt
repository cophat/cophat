package com.jodi.cophat.feature.patient.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityConfigureBinding
import com.jodi.cophat.feature.patient.viewmodel.PatientViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatientActivity : BaseActivity<ActivityConfigureBinding>() {

    private val viewModel: PatientViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_patient
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
