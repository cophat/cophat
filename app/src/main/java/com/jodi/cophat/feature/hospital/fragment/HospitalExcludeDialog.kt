package com.jodi.cophat.feature.hospital.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogHospitalExcludeBinding
import com.jodi.cophat.feature.hospital.viewmodel.HospitalViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HospitalExcludeDialog : BaseDialog<DialogHospitalExcludeBinding>() {

    private val viewModel: HospitalViewModel by viewModel()

    private val args: HospitalExcludeDialogArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.dialog_hospital_exclude
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_hospital_exclude"
    }

    override fun initBinding() {
        isCancelable = false

        viewModel.statusHospital.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_hospitalExcludeDialog_to_hospitalFragment)
        })

        hospitalListeners()
    }

    private fun hospitalListeners() {
        binding.bbvExclude.setBottomButtonsListener(object :
            BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.removeHospital(args.key)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}