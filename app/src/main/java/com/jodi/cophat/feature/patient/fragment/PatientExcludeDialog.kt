package com.jodi.cophat.feature.patient.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogConfigureExcludeBinding
import com.jodi.cophat.feature.patient.viewmodel.PatientViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatientExcludeDialog : BaseDialog<DialogConfigureExcludeBinding>() {

    private val viewModel: PatientViewModel by viewModel()

    private val args: PatientExcludeDialogArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.dialog_configure_exclude
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_configure_exclude"
    }

    override fun initBinding() {
        isCancelable = false

        viewModel.statusPatient.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_configureExcludeDialog_to_patientFragment)
        })

        configureListeners()
    }

    private fun configureListeners() {
        binding.bbvExclude.setBottomButtonsListener(object :
            BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.removePatient(args.key)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}