package com.jodi.cophat.ui.base.dialog

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel

class ErrorViewModel(private val resourceManager: ResourceManager) : BaseViewModel() {

    val errorMessage = MutableLiveData<String>()

    override fun initialize() {

    }

    fun handleThrowable(throwable: Throwable) {
        if (throwable is DatabaseException) {
            errorMessage.value = resourceManager.getString(R.string.firebase_error)
        } else {
            errorMessage.value = throwable.message
        }
    }
}