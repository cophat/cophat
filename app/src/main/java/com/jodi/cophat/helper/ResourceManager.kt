package com.jodi.cophat.helper

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourceManager constructor(var context: Context) {

    fun getString(stringRes: Int): String {
        return context.resources.getString(stringRes)
    }

    fun getString(stringRes: Int, vararg formatArgs: Any): String {
        return context.resources.getString(stringRes, formatArgs)
    }

    fun getDrawable(drawableRes: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableRes)
    }

    fun getDimension(dimensionRes: Int): Float {
        return context.resources.getDimension(dimensionRes)
    }

    fun getDimensionPixelSize(dimensionRes: Int): Int {
        return context.resources.getDimensionPixelSize(dimensionRes)
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}