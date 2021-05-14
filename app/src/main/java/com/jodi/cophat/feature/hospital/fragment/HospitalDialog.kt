package com.jodi.cophat.feature.hospital.fragment

import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogHospitalBinding
import com.jodi.cophat.feature.hospital.viewmodel.HospitalViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HospitalDialog : BaseDialog<DialogHospitalBinding>() {

    private val viewModel: HospitalViewModel by viewModel()

    private val args: HospitalDialogArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.dialog_hospital
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_hospital"
    }

    override fun initBinding() {
        isCancelable = false

        binding.presenter = args.presenter

        binding.presenter?.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.verifyDialogPresenter(binding.presenter)
            }
        })

        viewModel.isButtonEnabled.observe(this, Observer {
            binding.bbvHospital.updatePrimaryButton(it)
        })

        viewModel.statusHospital.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_hospitalDialog_to_hospitalFragment)
        })

        hospitalListeners()
    }

    private fun hospitalListeners() {
        binding.bbvHospital.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.saveOrUpdateHospital(binding.presenter, args.key)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}