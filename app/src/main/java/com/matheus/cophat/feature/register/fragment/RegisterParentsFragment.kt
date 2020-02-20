package com.matheus.cophat.feature.register.fragment

import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.matheus.cophat.R
import com.matheus.cophat.data.local.entity.MaritalStatusType
import com.matheus.cophat.data.local.entity.ReligionType
import com.matheus.cophat.databinding.FragmentRegisterParentsBinding
import com.matheus.cophat.feature.register.viewmodel.RegisterParentsViewModel
import com.matheus.cophat.ui.BaseFragment
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterParentsFragment : BaseFragment<FragmentRegisterParentsBinding>() {

    private val viewModel: RegisterParentsViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_register_parents
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleRegister,
            binding.tvSubtitleRegister,
            binding.tilMotherRegister,
            binding.tilFatherRegister,
            binding.tvMaritalStatusRegister,
            binding.rgMaritalStatusRegister,
            binding.tvReligionRegister,
            binding.rgReligionRegister,
            binding.bbvRegister
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
        binding.rgMaritalStatusRegister.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMarriedRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.MARRIED
                binding.rbAmassedRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.AMASSED
                binding.rbDivorcedRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.DIVORCED_SEPARATED
                binding.rbSingleRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.SINGLE
                binding.rbWidowerRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.WIDOWER
            }
        }

        binding.rgReligionRegister.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbCatholicRegister.id ->
                    binding.presenter?.religionType = ReligionType.CATHOLIC
                binding.rbEvangelicalRegister.id ->
                    binding.presenter?.religionType = ReligionType.EVANGELICAL
                binding.rbSpiritistRegister.id ->
                    binding.presenter?.religionType = ReligionType.SPIRITIST
                binding.rbOtherRegister.id ->
                    binding.presenter?.religionType = ReligionType.OTHER
                binding.rbNoneRegister.id ->
                    binding.presenter?.religionType = ReligionType.NONE
            }
            when (checkedId) {
                binding.rbOtherRegister.id -> binding.etOtherRegister.isEnabled = true
                else -> {
                    binding.etOtherRegister.isEnabled = false
                    binding.presenter?.religion = ""
                }
            }
        }

        binding.bbvRegister.setBottomButtonsListener(object : BottomButtonsListener {
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
            binding.bbvRegister.updatePrimaryButton(it)
        })

        viewModel.navigate.observe(this, Observer {
            findNavController().navigate(it)
        })
    }
}
