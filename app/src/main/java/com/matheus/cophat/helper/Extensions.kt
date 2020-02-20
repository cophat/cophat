package com.matheus.cophat.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Boolean.visibleOrGone() = if (this) 0 else 8

fun Int?.visibleOrGone() = if (this == 0) View.VISIBLE else View.GONE

fun String.isValidEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidDate(format: String, locale: Locale = Locale.getDefault()): Boolean {
    val formatter = SimpleDateFormat(format, locale)
    formatter.isLenient = false
    return try {
        formatter.parse(this)
        true
    } catch (e: ParseException) {
        false
    }
}

fun String.isBeforeToday(format: String, locale: Locale = Locale.getDefault()): Boolean {
    val formatter = SimpleDateFormat(format, locale)
    formatter.isLenient = false
    try {
        formatter.parse(this)?.let {
            return it.before(Calendar.getInstance().time)
        }
        return false
    } catch (e: ParseException) {
        return false
    }
}

fun Calendar.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this.time)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}