package com.jodi.cophat.feature.configure.fragment

import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogAdminBinding
import com.jodi.cophat.feature.configure.viewmodel.ConfigureViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminDialog : BaseDialog<DialogAdminBinding>() {

    private val viewModel: ConfigureViewModel by viewModel()

    private val args: AdminDialogArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.dialog_admin
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_admin"
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
            binding.bbvAdmin.updatePrimaryButton(it)
        })

        viewModel.statusAdmin.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_adminDialog_to_adminFragment)
        })

        configureListeners()
    }

    private fun configureListeners() {
        binding.bbvAdmin.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.saveOrUpdateAdmin(binding.presenter, args.key)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}