package com.jodi.cophat.feature.register.fragment

import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.RelationshipType
import com.jodi.cophat.databinding.FragmentRegisterParentsBinding
import com.jodi.cophat.feature.register.viewmodel.RegisterParentsViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
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
            binding.tilIntervieweeName,
            binding.tvRelationship,
            binding.rgRelationship,
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
        binding.rgRelationship.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbFatherRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.FATHER
                binding.rbMotherRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.MOTHER
                binding.rbGrandmotherpRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.GRAND_MOTHER_P
                binding.rbGrandfatherpRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.GRAND_FATHER_P
                binding.rbGrandmothermRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.GRAND_MOTHER_M
                binding.rbGrandfathermRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.GRAND_FATHER_M
                binding.rbOtherRelationshipRegister.id ->
                    binding.presenter?.relationshipType = RelationshipType.OTHER
            }
            when (checkedId) {
                binding.rbOtherRelationshipRegister.id -> binding.etOtherRelationshipRegister.isEnabled = true
                else -> {
                    binding.etOtherRelationshipRegister.isEnabled = false
                    binding.presenter?.relationship = ""
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
