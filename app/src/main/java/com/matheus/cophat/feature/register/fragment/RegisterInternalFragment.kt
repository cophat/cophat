package com.matheus.cophat.feature.register.fragment

import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.matheus.cophat.R
import com.matheus.cophat.databinding.FragmentRegisterInternalBinding
import com.matheus.cophat.feature.register.viewmodel.RegisterInternalViewModel
import com.matheus.cophat.ui.BaseFragment
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterInternalFragment : BaseFragment<FragmentRegisterInternalBinding>() {

    private val viewModel: RegisterInternalViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_register_internal
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleInternal,
            binding.tvSubtitleInternal,
            binding.tilDiagnosisInternal,
            binding.tilDiagnosisTimeInternal,
            binding.tilDaysInternal,
            binding.tilHospitalizationsInternal,
            binding.bbvInternal
        )
        binding.presenter = viewModel.presenter

        binding.presenter?.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.validatePresenter()
            }
        })

        configureListeners()
        configureObservers()

        viewModel.initialize()
    }

    private fun configureListeners() {
        binding.bbvInternal.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.updateApplication()
            }

            override fun onSecondaryClick() {
                activity?.onBackPressed()
            }
        })
    }

    private fun configureObservers() {
        viewModel.isButtonEnabled.observe(this, Observer {
            binding.bbvInternal.updatePrimaryButton(it)
        })

        viewModel.navigate.observe(this, Observer {
            findNavController().navigate(it)
        })
    }
}