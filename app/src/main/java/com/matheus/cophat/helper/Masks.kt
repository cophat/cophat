package com.matheus.cophat.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

class DateMask(private val input: EditText) : TextWatcher {

    private val mask = "##/##/####"
    private var isUpdating: Boolean = false
    private var oldTxt = ""

    init {
        this.input.addTextChangedListener(this)
    }

    private fun unmask(s: String): String {
        return s.replace("[^-?0-9]+".toRegex(), "")
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.toString() == oldTxt) {
            return
        }

        val str = unmask(s.toString())
        var maskCurrent = ""
        if (isUpdating) {
            oldTxt = str
            isUpdating = false
            return
        }
        var i = 0
        for (m in mask.toCharArray()) {
            if (m != '#' && str.length > oldTxt.length) {
                maskCurrent += m
                continue
            }
            try {
                maskCurrent += str[i]
            } catch (e: Exception) {
                break
            }

            i++
        }
        isUpdating = true
        input.setText(maskCurrent)
        input.setSelection(maskCurrent.length)
    }

    override fun afterTextChanged(s: Editable) {

    }
}

class MoneyMask(private val input: EditText) : TextWatcher {

    private var current = ""
    private val locale = Locale("pt", "BR")

    init {
        this.input.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.toString() != current) {
            input.removeTextChangedListener(this)

            val cleanString = s.toString().replace("[^-?0-9]+".toRegex(), "").trim()

            val formatted =
                NumberFormat.getCurrencyInstance(locale).format((cleanString.toDouble() / 100))

            current = formatted
            input.setText(formatted)
            input.setSelection(formatted.length)

            input.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }
}