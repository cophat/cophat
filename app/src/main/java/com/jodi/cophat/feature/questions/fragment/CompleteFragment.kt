package com.jodi.cophat.feature.questions.fragment

import android.animation.ValueAnimator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.jodi.cophat.R
import com.jodi.cophat.databinding.FragmentCompleteBinding
import com.jodi.cophat.feature.intro.activity.IntroActivity
import com.jodi.cophat.feature.questions.viewmodel.CompleteViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
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
            findNavController().navigate(R.id.splashFragment)

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