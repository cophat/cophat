package com.jodi.cophat.feature.intro.fragment

import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.StepsPresenter
import com.jodi.cophat.databinding.FragmentBeginBinding
import com.jodi.cophat.feature.intro.viewmodel.IntroViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeginFragment : BaseFragment<FragmentBeginBinding>() {

    private val viewModel: IntroViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_begin
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setViews(
            binding.ivImageBegin,
            binding.tvTitleBegin,
            binding.tvSubtitleBegin,
            binding.btFormBegin,
            binding.btCloseBegin,
            binding.btPatientBegin,
            binding.btListFormsBegin,
            binding.btConfigureBegin
        )

        configureListeners()

        viewModel.initialize()

        viewModel.beginPresenter.observe(this, Observer { binding.presenter = it })
    }

    private fun configureListeners() {
        binding.btFormBegin.setOnClickListener {
            lifecycleScope.launch {
                when (viewModel.chooseNavigation()) {
                    StepsPresenter.GENERATE_CODE_STEP_0 ->
                        findNavController().navigate(R.id.action_beginFragment_to_nav_generate)
                    StepsPresenter.CHILD_QUESTIONS ->
                        findNavController().navigate(R.id.action_beginFragment_to_nav_questions)
                    StepsPresenter.REGISTER_PARENTS_STEP_1 ->
                        findNavController().navigate(
                            BeginFragmentDirections.actionBeginFragmentToRegisterActivity(1)
                        )
                    StepsPresenter.REGISTER_PATIENT_STEP_2 ->
                        findNavController().navigate(
                            BeginFragmentDirections.actionBeginFragmentToRegisterActivity(2)
                        )
                    StepsPresenter.REGISTER_INTERNAL_STEP_3 ->
                        findNavController().navigate(
                            BeginFragmentDirections.actionBeginFragmentToRegisterActivity(3)
                        )
                    StepsPresenter.REGISTER_SCHOOL_STEP_4 ->
                        findNavController().navigate(
                            BeginFragmentDirections.actionBeginFragmentToRegisterActivity(4)
                        )
                    StepsPresenter.CONTINUE_QUESTIONNAIRE ->
                        findNavController().navigate(R.id.action_beginFragment_to_nav_questions)
                }
            }
        }

        binding.btCloseBegin.setOnClickListener {
            findNavController().navigate(R.id.action_beginFragment_to_excludeApplicationDialog)
        }

        binding.btPatientBegin.setOnClickListener {
            findNavController().navigate(R.id.action_beginFragment_to_nav_patient)
        }

        binding.btListFormsBegin.setOnClickListener {
            findNavController().navigate(R.id.action_beginFragment_to_questionnairesActivity)
        }

        binding.btConfigureBegin.setOnClickListener {
            findNavController().navigate(R.id.action_beginFragment_to_nav_configure)
        }
    }
}
