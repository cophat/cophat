package com.matheus.cophat.feature.intro.fragment

import android.animation.Animator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.matheus.cophat.R
import com.matheus.cophat.databinding.FragmentSplashBinding
import com.matheus.cophat.feature.intro.viewmodel.IntroViewModel
import com.matheus.cophat.helper.CustomAnimator
import com.matheus.cophat.helper.OnAnimationEndListener
import com.matheus.cophat.ui.BaseFragment
import com.matheus.cophat.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: IntroViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_splash
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        animateLauncher()
    }

    private fun animateLauncher() {
        YoYo.with(Techniques.BounceIn)
            .duration(900)
            .interpolate(AccelerateDecelerateInterpolator())
            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
            .withListener(CustomAnimator(object : OnAnimationEndListener {
                override fun onAnimationEnd(animation: Animator?) {
                    findNavController().navigate(R.id.action_splashFragment_to_beginFragment)
                }
            }))
            .playOn(binding.ivLauncherSplash)
    }
}
