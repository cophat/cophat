package com.jodi.cophat.feature.questions.adapter

import android.view.View
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemSubQuestionPresenter
import com.jodi.cophat.ui.BaseRecyclerView

class SubQuestionRecyclerAdapter :
    BaseRecyclerView<ItemSubQuestionPresenter, SubQuestionViewHolder>() {

    lateinit var subQuestionListener: SubQuestionListener

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_sub_question
    }

    override fun getViewHolderInstance(itemView: View, viewType: Int): SubQuestionViewHolder {
        return SubQuestionViewHolder(itemView, subQuestionListener)
    }

    override fun onBindViewHolder(
        holder: SubQuestionViewHolder,
        item: ItemSubQuestionPresenter,
        position: Int
    ) {
        holder.bind(item, position)
    }
}