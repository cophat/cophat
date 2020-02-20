package com.matheus.cophat.ui

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : ViewDataBinding, P>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal val binding: T? = DataBindingUtil.bind(itemView)

    abstract fun bind(presenter: P, position: Int)
}