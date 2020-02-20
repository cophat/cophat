package com.matheus.cophat.ui.base.dialog

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseException
import com.matheus.cophat.R
import com.matheus.cophat.helper.ResourceManager
import com.matheus.cophat.ui.BaseViewModel

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