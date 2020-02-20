package com.matheus.cophat.helper

import android.view.View
import android.widget.AdapterView

interface OnOnlyItemSelectedListener {

    fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
}

class CustomSpinnerListener(private val listener: OnOnlyItemSelectedListener) :
    AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        listener.onItemSelected(parent, view, position, id)
    }
}