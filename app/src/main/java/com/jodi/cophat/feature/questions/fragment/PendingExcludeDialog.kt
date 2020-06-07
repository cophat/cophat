package com.jodi.cophat.feature.questions.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogPendingExcludeBinding
import com.jodi.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PendingExcludeDialog : BaseDialog<DialogPendingExcludeBinding>() {

    private val viewModel: QuestionsViewModel by viewModel()

    private val args: PendingExcludeDialogArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.dialog_pending_exclude
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_pending_exclude"
    }

    override fun initBinding() {
        isCancelable = false

        viewModel.statusPending.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_configureExcludeDialog_to_pendingFragment)
        })

        configureListeners()
    }

    private fun configureListeners() {
        binding.bbvExclude.setBottomButtonsListener(object :
            BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.removePending(args.keyQuestionnaire, args.parentPosition?.toInt(), args.typeInterviewee)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }
}