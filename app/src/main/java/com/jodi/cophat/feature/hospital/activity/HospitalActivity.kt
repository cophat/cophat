package com.jodi.cophat.feature.hospital.activity

import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityHospitalBinding
import com.jodi.cophat.feature.hospital.viewmodel.HospitalViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HospitalActivity : BaseActivity<ActivityHospitalBinding>() {

    private val viewModel: HospitalViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.activity_hospital
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {

    }
}
