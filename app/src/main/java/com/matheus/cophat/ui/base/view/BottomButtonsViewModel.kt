package com.matheus.cophat.ui.base.view

import com.matheus.cophat.R
import com.matheus.cophat.helper.ResourceManager
import com.matheus.cophat.ui.BaseViewModel

class BottomButtonsViewModel(private val resourceManager: ResourceManager) : BaseViewModel() {

    override fun initialize() {

    }

    fun bottomButtonsMargin(isSmall: Boolean): Int {
        return if (isSmall) resourceManager.getDimensionPixelSize(R.dimen.small_margin) else resourceManager.getDimensionPixelSize(
            R.dimen.button_margin
        )
    }

    fun primaryButtonAlpha(isEnabled: Boolean) = if (isEnabled) 1f else 0.5f
}