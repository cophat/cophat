package com.jodi.cophat.feature.questions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.CompletePresenter
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompleteViewModel(private val resourceManager: ResourceManager) : BaseViewModel() {

    val presenter = MutableLiveData<CompletePresenter>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                val completePresenter = if (isChildren) {
                    CompletePresenter(resourceManager.getString(R.string.thanks_children))
                } else {
                    CompletePresenter(resourceManager.getString(R.string.thanks_parents))
                }
                presenter.postValue(completePresenter)
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}