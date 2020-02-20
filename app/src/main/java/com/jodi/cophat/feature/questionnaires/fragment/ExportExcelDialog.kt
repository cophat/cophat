package com.jodi.cophat.feature.questionnaires.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.databinding.DialogExportExcelBinding
import com.jodi.cophat.feature.questionnaires.viewmodel.ExportExcelViewModel
import com.jodi.cophat.helper.ExportListener
import com.jodi.cophat.helper.ExportWorkbook
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
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