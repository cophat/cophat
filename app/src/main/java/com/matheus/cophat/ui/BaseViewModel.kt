package com.matheus.cophat.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheus.cophat.BuildConfig

abstract class BaseViewModel : ViewModel() {

    val isButtonEnabled = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val isChildren = BuildConfig.FLAVOR == "children"
    val hasPermission = MutableLiveData<Boolean>()
    val handleError = MutableLiveData<Throwable>()

    abstract fun initialize()
}