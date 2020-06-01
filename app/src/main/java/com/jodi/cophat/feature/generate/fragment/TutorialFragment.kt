package com.jodi.cophat.feature.generate.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.databinding.FragmentTutorialBinding
import com.jodi.cophat.feature.generate.viewmodel.GenerateCodeViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TutorialFragment : BaseFragment<FragmentTutorialBinding>() {

    private val viewModel: GenerateCodeViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_tutorial
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        binding.bbvTutorial.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                findNavController().navigate(R.id.action_tutorialFragment_to_nav_questions)
            }

            override fun onSecondaryClick() {
                activity?.onBackPressed()
            }
        })
    }
}
