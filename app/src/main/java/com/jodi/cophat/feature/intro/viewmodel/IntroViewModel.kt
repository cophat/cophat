package com.jodi.cophat.feature.intro.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.presenter.BeginPresenter
import com.jodi.cophat.data.presenter.StepsPresenter
import com.jodi.cophat.data.repository.IntroRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroViewModel(
    private val repository: IntroRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    private var application: ApplicationEntity? = null
    val beginPresenter = MutableLiveData<BeginPresenter>()
    val statusApplication = MutableLiveData<String>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                val presenterImage: Int
                val presenterTitle: String
                val presenterSubtitle: String
                val hasRepository = !repository.isEmpty()

                if (isChildren) {
                    presenterImage = R.drawable.ic_launcher
                    presenterTitle = resourceManager.getString(R.string.cophat_ca)
                    presenterSubtitle = resourceManager.getString(R.string.cophat_ca_desc)
                } else {
                    presenterImage = R.drawable.ic_launcher
                    presenterTitle = resourceManager.getString(R.string.cophat_p)
                    presenterSubtitle = resourceManager.getString(R.string.cophat_p_desc)
                }

                beginPresenter.postValue(
                    BeginPresenter(
                        presenterImage,
                        presenterTitle,
                        presenterSubtitle,
                        hasRepository.visibleOrGone()
                    )
                )
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    suspend fun chooseNavigation(): StepsPresenter? {
        try {
            isLoading.postValue(true)

            application = repository.getApplication()
            return when {
                application == null ->
                    StepsPresenter.GENERATE_CODE_STEP_0
                isChildren ->
                    StepsPresenter.CHILD_QUESTIONS
                application?.identificationCode == null ->
                    StepsPresenter.REGISTER_PARENTS_STEP_1
                else ->
                    StepsPresenter.GENERATE_CODE_STEP_0
            }
        } catch (e: DatabaseException) {
            handleError.postValue(e)
            return null
        } finally {
            isLoading.postValue(false)
        }
    }

    fun deleteApplication() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                repository.clearLocally()

            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}