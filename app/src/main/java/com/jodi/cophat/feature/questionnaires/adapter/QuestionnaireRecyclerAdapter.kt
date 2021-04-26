// Arquivo novo

package com.jodi.cophat.feature.patient.adapter

import android.view.View
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemQuestionnairePresenter
import com.jodi.cophat.data.presenter.QuestionnaireReportPresenter
import com.jodi.cophat.feature.questionnaires.adapter.QuestionnaireListener
import com.jodi.cophat.feature.questionnaires.adapter.QuestionnaireViewHolder
import com.jodi.cophat.ui.BaseRecyclerView

class QuestionnaireRecyclerAdapter :
    BaseRecyclerView<ItemQuestionnairePresenter, QuestionnaireViewHolder>() {

    lateinit var questionnaireListener: QuestionnaireListener

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_questionnaire
    }

    override fun getViewHolderInstance(itemView: View, viewType: Int): QuestionnaireViewHolder {
        return QuestionnaireViewHolder(itemView, questionnaireListener)
    }

    override fun onBindViewHolder(
        holder: QuestionnaireViewHolder,
        item: ItemQuestionnairePresenter,
        position: Int
    ) {
        holder.bind(item, position)
    }
}