package com.matheus.cophat.feature.questions.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.matheus.cophat.R
import com.matheus.cophat.data.local.entity.AnswerType
import com.matheus.cophat.databinding.FragmentQuestionsBinding
import com.matheus.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.matheus.cophat.ui.BaseFragment
import com.matheus.cophat.ui.BaseViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsListener
import com.matheus.cophat.ui.base.view.ThermometerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionsFragment : BaseFragment<FragmentQuestionsBinding>() {

    private val viewModel: QuestionsViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_questions
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvCodeQuestions,
            binding.tvStateQuestions,
            binding.pbStatusQuestions,
            binding.tvStatementQuestions,
            binding.vQuestions,
            binding.bbvQuestions
        )
        binding.presenter = viewModel.presenter

        configureListeners()
        configureObservers()

        viewModel.initialize()
    }

    private fun configureListeners() {
        binding.vQuestions.setThermometerListener(object : ThermometerListener {
            override fun onAnswerChanged(answerType: AnswerType) {
                viewModel.presenter.answer = answerType
            }
        })

        binding.bbvQuestions.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.updateApplication()
            }

            override fun onSecondaryClick() {
                activity?.onBackPressed()
            }
        })
    }

    private fun configureObservers() {
        viewModel.subQuestion.observe(this, Observer {
            findNavController().navigate(
                QuestionsFragmentDirections.actionQuestionsFragmentToSubQuestionDialog(it)
            )
        })
        viewModel.completeQuestionnaire.observe(this, Observer {
            findNavController().navigate(it)
        })
    }
}