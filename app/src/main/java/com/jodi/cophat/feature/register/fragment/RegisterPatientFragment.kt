//// Apagar?
//
//package com.jodi.cophat.feature.register.fragment
//
//import androidx.databinding.Observable
//import androidx.lifecycle.Observer
//import androidx.navigation.fragment.findNavController
//import com.jodi.cophat.R
//import com.jodi.cophat.databinding.FragmentRegisterPatientBinding
//import com.jodi.cophat.feature.register.viewmodel.RegisterPatientViewModel
//import com.jodi.cophat.helper.DateMask
//import com.jodi.cophat.ui.BaseFragment
//import com.jodi.cophat.ui.BaseViewModel
//import com.jodi.cophat.ui.base.view.BottomButtonsListener
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//class RegisterPatientFragment : BaseFragment<FragmentRegisterPatientBinding>() {
//
//    private val viewModel: RegisterPatientViewModel by viewModel()
//
//    override fun getLayout(): Int {
//        return R.layout.fragment_register_patient
//    }
//
//    override fun getViewModel(): BaseViewModel {
//        return viewModel
//    }
//
//    override fun initBinding() {
//        setViews(
//            binding.tvTitlePatient,
//            binding.tvSubtitlePatient,
//            binding.tilNamePatient,
//            binding.tilMedicalRecordsPatient,
//            binding.tilIdentifyCodePatient,
//            binding.tilBirthdayPatient,
//            binding.tilAgePatient,//string
//            binding.tvGenderPatient,
//            binding.rbMalePatient,
//            binding.rbFemalePatient,
//            binding.bbvPatient
//        )
//        binding.presenter = viewModel.presenter
//
//        binding.presenter?.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                viewModel.validatePresenter()
//            }
//        })
//
//        configureListeners()
//        configureObservers()
//
//        viewModel.initialize()
//    }
//
//    private fun configureListeners() {
//        DateMask(binding.etBirthdayPatient)
//
//        binding.bbvPatient.setBottomButtonsListener(object : BottomButtonsListener {
//            override fun onPrimaryClick() {
//                viewModel.updateApplication()
//            }
//
//            override fun onSecondaryClick() {
//                activity?.onBackPressed()
//            }
//        })
//    }
//
//    private fun configureObservers() {
//        viewModel.isButtonEnabled.observe(this, Observer {
//            binding.bbvPatient.updatePrimaryButton(it)
//        })
//
//        viewModel.navigate.observe(this, Observer {
//            findNavController().navigate(it)
//        })
//    }
//}