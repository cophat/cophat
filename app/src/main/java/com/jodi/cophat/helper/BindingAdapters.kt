package com.jodi.cophat.helper

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("android:progress")
fun setAnimatedProgress(progressBar: ProgressBar, progress: Int) {
    val animator = ObjectAnimator.ofInt(progressBar, "progress", progress)
    animator.startDelay = 100
    animator.duration = 300
    animator.interpolator = AccelerateDecelerateInterpolator()
    animator.start()
}