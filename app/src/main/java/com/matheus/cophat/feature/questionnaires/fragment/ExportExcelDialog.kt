package com.matheus.cophat.feature.questionnaires.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.matheus.cophat.R
import com.matheus.cophat.databinding.DialogExportExcelBinding
import com.matheus.cophat.feature.questionnaires.viewmodel.ExportExcelViewModel
import com.matheus.cophat.helper.ExportListener
import com.matheus.cophat.helper.ExportWorkbook
import com.matheus.cophat.helper.showToast
import com.matheus.cophat.ui.BaseDialog
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExportExcelDialog : BaseDialog<DialogExportExcelBinding>(), ExportListener {

    private val viewModel: ExportExcelViewModel by viewModel()
    private val args: ExportExcelDialogArgs by navArgs()
    private val exportWorkbook: ExportWorkbook by inject()

    override fun getLayout(): Int {
        return R.layout.dialog_export_excel
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_export_excel"
    }

    override fun initBinding() {
        isCancelable = false

        binding.bbvExcel.setBottomButtonsListener(object :
            BottomButtonsListener {
            override fun onPrimaryClick() {
                binding.bbvExcel.binding.btPrimary.isEnabled = false
                lifecycleScope.launch {
                    val categories = viewModel.getCategories()
                    val questions = viewModel.getQuestions()

                    exportWorkbook.exportQuestionnaires(
                        args.questionnaires,
                        categories,
                        questions,
                        this@ExportExcelDialog
                    )
                }
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }

    override fun onExportSuccess() {
        context?.showToast(getString(R.string.export_success))
        dismiss()
    }

    override fun onExportFailed() {
        context?.showToast(getString(R.string.export_failed))
        dismiss()
    }
}