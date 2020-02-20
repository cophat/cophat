package com.matheus.cophat.data.local.entity

data class Admin(
    var name: String = "",
    var contact: String = ""
) {
    override fun toString(): String {
        return name
    }
}