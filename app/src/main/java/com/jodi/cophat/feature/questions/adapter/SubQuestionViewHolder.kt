package com.jodi.cophat.feature.questions.adapter

import android.view.View
import androidx.core.widget.doOnTextChanged
import com.jodi.cophat.data.local.entity.AnswerType
import com.jodi.cophat.data.local.entity.SubAnswerType
import com.jodi.cophat.data.presenter.ItemSubQuestionPresenter
import com.jodi.cophat.databinding.ItemSubQuestionBinding
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewHolder

class SubQuestionViewHolder(itemView: View, private val subQuestionListener: SubQuestionListener) :
    BaseViewHolder<ItemSubQuestionBinding, ItemSubQuestionPresenter>(itemView) {

    override fun bind(presenter: ItemSubQuestionPresenter, position: Int) {
        binding?.tvAlternative?.text = presenter.description
        binding?.tvAlternative?.visibility = presenter.descriptionVisibility.visibleOrGone()
        binding?.tilAlternative?.hint = presenter.description
        binding?.tilAlternative?.visibility = presenter.otherVisibility.visibleOrGone()
        binding?.etAlternative?.isEnabled = presenter.alternativeIsEnabled
        binding?.etAlternative?.doOnTextChanged { text, _, _, _ ->
            presenter.other = text.toString()
            subQuestionListener.onSubAnswerOtherChanged(presenter)
        }

        binding?.rgThermometerAlternative?.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbAlwaysAlternative.id ->
                    presenter.chosenSubAnswer = AnswerType.ALWAYS
                binding.rbOftenAlternative.id ->
                    presenter.chosenSubAnswer = AnswerType.OFTEN
                binding.rbSometimesAlternative.id ->
                    presenter.chosenSubAnswer = AnswerType.SOMETIMES
                binding.rbAlmostNeverAlternative.id ->
                    presenter.chosenSubAnswer = AnswerType.ALMOST_NEVER
                binding.rbNeverAlternative.id ->
                    presenter.chosenSubAnswer = AnswerType.NEVER
            }
            when (checkedId) {
                binding.rbNeverAlternative.id -> {
                    if (presenter.type != SubAnswerType.OPEN) {
                        binding.etAlternative.isEnabled = false
                        binding.etAlternative.text?.clear()
                    }
                }
                else ->
                    binding.etAlternative.isEnabled = true
            }
            subQuestionListener.onSubAnswerChanged(presenter)
        }
    }
}