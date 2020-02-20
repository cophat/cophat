package com.matheus.cophat.ui.base.dialog

import androidx.lifecycle.Observer
import com.matheus.cophat.R
import com.matheus.cophat.databinding.DialogErrorBinding
import com.matheus.cophat.ui.BaseDialog
import com.matheus.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ErrorDialog : BaseDialog<DialogErrorBinding>() {

    private val viewModel: ErrorViewModel by viewModel()

    companion object {
        private const val TAG = "dialog_error"

        @Synchronized
        fun newInstance(): ErrorDialog {
            return ErrorDialog()
        }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_error
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return TAG
    }

    override fun initBinding() {
        binding.btError.setOnClickListener { dismiss() }

        viewModel.errorMessage.observe(this,
            Observer { binding.tvMessageError.text = it })
    }

    fun handleThrowable(throwable: Throwable?) {
        throwable?.let { viewModel.handleThrowable(it) }
    }
}