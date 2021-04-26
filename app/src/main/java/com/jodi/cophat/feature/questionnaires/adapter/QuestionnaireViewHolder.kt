package com.jodi.cophat.feature.questionnaires.adapter

import android.view.View
import com.jodi.cophat.data.presenter.ItemQuestionnairePresenter
import com.jodi.cophat.databinding.ItemQuestionnaireBinding
import com.jodi.cophat.ui.BaseViewHolder

class QuestionnaireViewHolder(
    itemView: View,
    private val questionnaireListener: QuestionnaireListener
) : BaseViewHolder<ItemQuestionnaireBinding, ItemQuestionnairePresenter>(itemView) {

    override fun bind(presenter: ItemQuestionnairePresenter, position: Int) {
        binding?.let {
            it.presenter = presenter
            it.tvChildrenQuestionnaire.setCompoundDrawablesWithIntrinsicBounds(
                presenter.childrenDrawable,
                0,
                0,
                0
            )
            it.tvExcelQuestionnaire.setOnClickListener {
                questionnaireListener.onClickExcel(presenter.questionnaireReport)
            }
        }
    }
}