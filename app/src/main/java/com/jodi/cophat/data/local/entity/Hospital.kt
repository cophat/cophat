package com.jodi.cophat.data.local.entity

data class Hospital(
    var code: String = "",
    var name: String = ""
) {
    override fun toString(): String {
        return name
    }
}