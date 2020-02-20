package com.matheus.cophat.feature.configure.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.matheus.cophat.R
import com.matheus.cophat.databinding.DialogConfigureExcludeBinding
import com.matheus.cophat.feature.configure.viewmodel.ConfigureViewModel
import com.matheus.cophat.helper.showToast
import com.matheus.cophat.ui.BaseDialog
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfigureExcludeDialog : BaseDialog<DialogConfigureExcludeBinding>() {

    private val viewModel: ConfigureViewModel by viewModel()

    private val args: ConfigureExcludeDialogArgs by navArgs()

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

        viewModel.statusAdmin.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_configureExcludeDialog_to_adminFragment)
        })

        configureListeners()
    }

    private fun configureListeners() {
        binding.bbvExclude.setBottomButtonsListener(object :
            BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.removeAdmin(args.key)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}