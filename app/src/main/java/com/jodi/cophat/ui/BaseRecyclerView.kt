package com.jodi.cophat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    internal var presenterList = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        return getViewHolderInstance(itemView, viewType)
    }

    override fun getItemCount(): Int {
        return presenterList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, presenterList[position], position)
    }

    fun setItems(items : List<T>) {
        presenterList.clear()
        presenterList.addAll(items)
        notifyDataSetChanged()
    }

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun getViewHolderInstance(itemView: View, viewType: Int): VH

    abstract fun onBindViewHolder(holder: VH, item: T, position: Int)

}