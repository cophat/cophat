package com.jodi.cophat.feature.questions.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemSubQuestionPresenter
import com.jodi.cophat.databinding.DialogSubQuestionBinding
import com.jodi.cophat.feature.questions.adapter.SubQuestionListener
import com.jodi.cophat.feature.questions.adapter.SubQuestionRecyclerAdapter
import com.jodi.cophat.feature.questions.viewmodel.SubQuestionViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubQuestionDialog : BaseDialog<DialogSubQuestionBinding>(), SubQuestionListener {

    private val viewModel: SubQuestionViewModel by viewModel()
    private val adapter: SubQuestionRecyclerAdapter by inject()
    private val args: SubQuestionDialogArgs by navArgs()

    override fun getLayout(): Int {
        return R.layout.dialog_sub_question
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_sub_question"
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleSubQuestion,
            binding.fSubQuestion,
            binding.bbvSubQuestion
        )
        isCancelable = false

        viewModel.initialize()
        viewModel.presenter = args.subQuestion

        configureArgs()
        configureListeners()
        configureObservers()
        configureAdapter()
    }

    private fun configureArgs() {
        viewModel.statement.observe(this, Observer {
            binding.tvTitleSubQuestion.text = it
        })
    }

    private fun configureAdapter() {
        adapter.subQuestionListener = this
        binding.rvSubQuestion.adapter = adapter
        viewModel.getAlternatives()?.let {
            binding.rvSubQuestion.setItemViewCacheSize(it.size)
            adapter.setItems(it)
        }
    }

    private fun configureListeners() {
        binding.bbvSubQuestion.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                saveAndRestartQuestionnaire()
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }

    private fun saveAndRestartQuestionnaire() {
        lifecycleScope.launch {
            viewModel.saveAlternatives(adapter.presenterList)
            findNavController().navigate(R.id.action_subQuestionDialog_to_questionsFragment)
            dismiss()
        }
    }

    private fun configureObservers() {
        viewModel.isPrimaryButtonEnabled.observe(this, Observer {
            binding.bbvSubQuestion.updatePrimaryButton(it)
        })
    }

    override fun onSubAnswerChanged(item: ItemSubQuestionPresenter) {
        binding.bbvSubQuestion.updatePrimaryButton(viewModel.isValidItem(item))
        context?.showToast(item.type?.chosenSubAnswer + " " + item.chosenSubAnswer?.chosenAnswer)
    }

    override fun onSubAnswerOtherChanged(item: ItemSubQuestionPresenter) {
        binding.bbvSubQuestion.updatePrimaryButton(viewModel.isValidItem(item))
    }
}