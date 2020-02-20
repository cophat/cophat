package com.jodi.cophat.feature.register.fragment

import android.widget.RadioGroup
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.EducationType
import com.jodi.cophat.data.local.entity.SchoolingType
import com.jodi.cophat.databinding.FragmentRegisterSchoolBinding
import com.jodi.cophat.feature.register.viewmodel.RegisterSchoolViewModel
import com.jodi.cophat.helper.MoneyMask
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterSchoolFragment : BaseFragment<FragmentRegisterSchoolBinding>() {

    private val viewModel: RegisterSchoolViewModel by viewModel()

    private lateinit var listener1: RadioGroup.OnCheckedChangeListener
    private lateinit var listener2: RadioGroup.OnCheckedChangeListener

    override fun getLayout(): Int {
        return R.layout.fragment_register_school
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleSchool,
            binding.tvSubtitleSchool,
            binding.tvSchoolingSchool,
            binding.rgSchoolingSchool1,
            binding.rgSchoolingSchool2,
            binding.tvOutSchool,
            binding.rgOutSchool,
            binding.tvResidentSchool,
            binding.rgResidentSchool,
            binding.tilAddressSchool,
            binding.tilIncomeSchool,
            binding.tvEducationSchool,
            binding.rgEducationSchool,
            binding.bbvSchool
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
        MoneyMask(binding.etIncomeSchool)

        listener1 = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            binding.rgSchoolingSchool2.setOnCheckedChangeListener(null)
            binding.rgSchoolingSchool2.clearCheck()
            binding.rgSchoolingSchool2.setOnCheckedChangeListener(listener2)
            when (checkedId) {
                binding.rbPreSchool.id ->
                    binding.presenter?.schooling = SchoolingType.PRE
                binding.rbFirstSchool.id ->
                    binding.presenter?.schooling = SchoolingType.FIRST_YEAR
                binding.rbSecondSchool.id ->
                    binding.presenter?.schooling = SchoolingType.SECOND_YEAR
                binding.rbThirdSchool.id ->
                    binding.presenter?.schooling = SchoolingType.THIRD_YEAR
                binding.rbFourthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.FOURTH_YEAR
            }
        }

        listener2 = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            binding.rgSchoolingSchool1.setOnCheckedChangeListener(null)
            binding.rgSchoolingSchool1.clearCheck()
            binding.rgSchoolingSchool1.setOnCheckedChangeListener(listener1)
            when (checkedId) {
                binding.rbFifthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.FIFTH_YEAR
                binding.rbSixthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.SIXTH_YEAR
                binding.rbSeventhSchool.id ->
                    binding.presenter?.schooling = SchoolingType.SEVENTH_YEAR
                binding.rbEighthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.EIGHTH_YEAR
                binding.rbNinthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.NINTH_YEAR
            }
        }

        binding.rgSchoolingSchool1.setOnCheckedChangeListener(listener1)

        binding.rgSchoolingSchool2.setOnCheckedChangeListener(listener2)

        binding.rgEducationSchool.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbIlliterateSchool.id ->
                    binding.presenter?.education = EducationType.ILLITERATE
                binding.rbFundamental1School.id ->
                    binding.presenter?.education = EducationType.COMPLETE_FUNDAMENTAL_I
                binding.rbFundamental2School.id ->
                    binding.presenter?.education = EducationType.COMPLETE_FUNDAMENTAL_II
                binding.rbMediumSchool.id ->
                    binding.presenter?.education = EducationType.COMPLETE_MEDIUM
                binding.rbGraduatedSchool.id ->
                    binding.presenter?.education = EducationType.GRADUATED
            }
        }

        binding.bbvSchool.setBottomButtonsListener(object : BottomButtonsListener {
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
            binding.bbvSchool.updatePrimaryButton(it)
        })

        viewModel.navigate.observe(this, Observer {
            findNavController().navigate(it)
        })
    }
}