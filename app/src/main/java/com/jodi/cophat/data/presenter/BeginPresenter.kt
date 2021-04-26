package com.jodi.cophat.data.presenter

import androidx.annotation.DrawableRes

data class BeginPresenter(
    @DrawableRes
    var beginImage: Int = 0,
    var beginTitle: String = "",
    var beginSubtitle: String = "",
    var beginVisibility: Int = 0
)