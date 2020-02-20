package com.matheus.cophat.feature.intro.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.matheus.cophat.R
import com.matheus.cophat.databinding.DialogExcludeApplicationBinding
import com.matheus.cophat.feature.intro.viewmodel.IntroViewModel
import com.matheus.cophat.helper.showToast
import com.matheus.cophat.ui.BaseDialog
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExcludeApplicationDialog : BaseDialog<DialogExcludeApplicationBinding>() {

    private val viewModel: IntroViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.dialog_exclude_application
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_exclude_application"
    }

    override fun initBinding() {
        isCancelable = false

        viewModel.statusApplication.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_excludeApplicationDialog_to_beginFragment)
        })

        configureListeners()
    }

    private fun configureListeners() {
        binding.bbvExclude.setBottomButtonsListener(object :
            BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.deleteApplication()
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}