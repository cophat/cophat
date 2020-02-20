package com.matheus.cophat.feature.questions.fragment

import android.animation.ValueAnimator
import androidx.lifecycle.Observer
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.matheus.cophat.R
import com.matheus.cophat.databinding.FragmentCompleteBinding
import com.matheus.cophat.feature.questions.viewmodel.CompleteViewModel
import com.matheus.cophat.ui.BaseFragment
import com.matheus.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompleteFragment : BaseFragment<FragmentCompleteBinding>() {

    private val viewModel: CompleteViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_complete
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleComplete,
            binding.ivComplete,
            binding.tvDescriptionComplete,
            binding.btComplete
        )

        configureListeners()
        configureObservers()
        animate()

        viewModel.initialize()
    }

    private fun configureListeners() {
        binding.btComplete.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun configureObservers() {
        viewModel.presenter.observe(this, Observer {
            binding.presenter = it
        })
    }

    private fun animate() {
        YoYo.with(Techniques.Bounce)
            .repeat(ValueAnimator.INFINITE)
            .playOn(binding.ivComplete)
    }
}