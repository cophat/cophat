package com.matheus.cophat.ui.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.matheus.cophat.R
import com.matheus.cophat.data.presenter.BottomButtonPresenter
import com.matheus.cophat.databinding.ViewBottomButtonsBinding
import com.matheus.cophat.helper.hideKeyboard
import org.koin.core.KoinComponent
import org.koin.core.inject

class BottomButtonsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), KoinComponent {

    private val viewModel: BottomButtonsViewModel by inject()

    lateinit var listener: BottomButtonsListener

    val binding: ViewBottomButtonsBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.view_bottom_buttons, this, true
        )

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.BottomButtonsView, defStyleAttr, defStyleRes
            )
            val primary = typedArray.getString(R.styleable.BottomButtonsView_bbv_primary_name)
                ?: resources.getString(R.string.advance)

            val secondary = typedArray.getString(R.styleable.BottomButtonsView_bbv_secondary_name)
                ?: resources.getString(R.string.go_back)

            val isEnabled = typedArray.getBoolean(
                R.styleable.BottomButtonsView_bbv_primary_enabled, false
            )

            val alpha = viewModel.primaryButtonAlpha(isEnabled)

            val isSmall = typedArray.getBoolean(
                R.styleable.BottomButtonsView_bbv_is_small, false
            )

            binding.buttonPresenter = BottomButtonPresenter(primary, secondary, isEnabled, alpha)

            configureButtonsListener()
            configureButtonsParams(isSmall)

            typedArray.recycle()
        }
    }

    private fun configureButtonsListener() {
        binding.btPrimary.setOnClickListener {
            hideKeyboard()
            listener.onPrimaryClick()
        }
        binding.btSecondary.setOnClickListener {
            hideKeyboard()
            listener.onSecondaryClick()
        }
    }

    private fun configureButtonsParams(isSmall: Boolean) {
        val margin = viewModel.bottomButtonsMargin(isSmall)

        val btPrimaryParams = binding.btPrimary.layoutParams as LayoutParams
        btPrimaryParams.setMargins(
            margin,
            resources.getDimensionPixelSize(R.dimen.extra_margin),
            margin,
            resources.getDimensionPixelSize(R.dimen.small_margin)
        )
        binding.btPrimary.layoutParams = btPrimaryParams

        val btSecondaryParams = binding.btSecondary.layoutParams as LayoutParams
        btSecondaryParams.setMargins(
            margin,
            resources.getDimensionPixelSize(R.dimen.extra_margin),
            margin,
            resources.getDimensionPixelSize(R.dimen.small_margin)
        )
        binding.btSecondary.layoutParams = btSecondaryParams
    }

    fun updatePrimaryButton(isButtonEnabled: Boolean) {
        binding.btPrimary.isEnabled = isButtonEnabled
        binding.btPrimary.alpha = viewModel.primaryButtonAlpha(isButtonEnabled)
    }

    fun setBottomButtonsListener(listener: BottomButtonsListener) {
        this.listener = listener
    }
}