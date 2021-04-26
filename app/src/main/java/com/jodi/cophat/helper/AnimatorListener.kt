package com.jodi.cophat.helper

import android.animation.Animator

interface OnAnimationEndListener {

    fun onAnimationEnd(animation: Animator?)
}

class CustomAnimator(private val onAnimationEndListener: OnAnimationEndListener) :
    Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {
        onAnimationEndListener.onAnimationEnd(animation)
    }

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationStart(animation: Animator?) {}

}