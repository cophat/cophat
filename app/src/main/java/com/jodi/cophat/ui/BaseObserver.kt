package com.jodi.cophat.ui

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jodi.cophat.ui.base.dialog.ErrorDialog
import com.jodi.cophat.ui.base.dialog.LoadingDialog

class BaseObserver constructor(
    private val baseViewModel: BaseViewModel,
    private val fragmentManager: FragmentManager?
) {

    val views = ArrayList<View>()

    fun observeChanges(owner: LifecycleOwner) {
        baseViewModel.isLoading.observe(
            owner,
            Observer { shouldLoading ->
                if (shouldLoading) {
                    showLoading()
                } else {
                    hideLoading()
                    fadeIn()
                }
            })

        baseViewModel.handleError.observe(owner, Observer { handleError(it) })
    }

    fun setViews(binding: Array<out View>) {
        views.clear()
        views.addAll(binding)

        for (view in views) {
            view.alpha = 0f
        }
    }

    private fun fadeIn() {
        var delay = 0L
        for (view in views) {
            view.animate().alpha(1f).setStartDelay(delay).start()
            delay += 100L
        }
    }

    private fun showLoading() {
        fragmentManager?.let { LoadingDialog.newInstance().show(it) }
    }

    private fun hideLoading() {
        (fragmentManager?.findFragmentByTag(LoadingDialog.TAG) as DialogFragment?)?.dismiss()
    }

    private fun handleError(throwable: Throwable) {
        fragmentManager?.let {
            val errorDialog = ErrorDialog.newInstance()
            errorDialog.show(it)
            errorDialog.handleThrowable(throwable)
        }
    }
}