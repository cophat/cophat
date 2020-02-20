package com.matheus.cophat.feature.configure.fragment

import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.matheus.cophat.R
import com.matheus.cophat.databinding.DialogAdminBinding
import com.matheus.cophat.feature.configure.viewmodel.ConfigureViewModel
import com.matheus.cophat.helper.showToast
import com.matheus.cophat.ui.BaseDialog
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
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