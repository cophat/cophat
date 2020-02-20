package com.jodi.cophat.feature.register.activity

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.ActivityRegisterBinding
import com.jodi.cophat.feature.register.viewmodel.RegisterParentsViewModel
import com.jodi.cophat.ui.BaseActivity
import com.jodi.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel: RegisterParentsViewModel by viewModel()

    private val args: RegisterActivityArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.activity_register
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fNavHostRegister) as NavHostFragment
        val graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.nav_register)

        when {
            args.step == 1 -> graph.startDestination = R.id.registerParentsFragment
            args.step == 2 -> graph.startDestination = R.id.registerPatientFragment
            args.step == 3 -> graph.startDestination = R.id.registerInternalFragment
            args.step == 4 -> graph.startDestination = R.id.registerSchoolFragment
        }
        navHostFragment.navController.graph = graph
    }
}