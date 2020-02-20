package com.jodi.cophat.feature.intro.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogExcludeApplicationBinding
import com.jodi.cophat.feature.intro.viewmodel.IntroViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
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