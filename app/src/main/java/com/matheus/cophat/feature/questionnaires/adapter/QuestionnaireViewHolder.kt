package com.matheus.cophat.feature.questionnaires.adapter

import android.view.View
import com.matheus.cophat.data.presenter.ItemQuestionnairePresenter
import com.matheus.cophat.databinding.ItemQuestionnaireBinding
import com.matheus.cophat.ui.BaseViewHolder

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
                questionnaireListener.onClickExcel(presenter.questionnaire)
            }
        }
    }
}