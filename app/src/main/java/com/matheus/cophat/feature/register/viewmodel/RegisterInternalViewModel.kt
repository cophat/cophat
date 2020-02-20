package com.matheus.cophat.feature.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.matheus.cophat.R
import com.matheus.cophat.data.local.entity.ApplicationEntity
import com.matheus.cophat.data.local.entity.GenderType
import com.matheus.cophat.data.presenter.RegisterInternalPresenter
import com.matheus.cophat.data.repository.RegisterRepository
import com.matheus.cophat.helper.ResourceManager
import com.matheus.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterInternalViewModel(
    private val repository: RegisterRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val presenter = RegisterInternalPresenter()
    var application: ApplicationEntity? = null
    val navigate = MutableLiveData<Int>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                application = repository.getApplication()

                presenter.subtitle = generateSubtitle()
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun validatePresenter() {
        if (presenter.diagnosis.trim().isNotEmpty() &&
            presenter.diagnosisTime.trim().isNotEmpty() &&
            presenter.daysHospitalized.trim().isNotEmpty() &&
            presenter.hospitalizations.trim().isNotEmpty()
        ) {
            isButtonEnabled.postValue(true)
        } else {
            isButtonEnabled.postValue(false)
        }
    }

    fun updateApplication() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                application?.let { application ->
                    val questionnaire = repository.getQuestionnaireByFamilyId(application.familyId)

                    val patient = application.patient
                    patient?.diagnosis = presenter.diagnosis
                    patient?.diagnosticTime = presenter.diagnosisTime
                    patient?.internedDays = Integer.valueOf(presenter.daysHospitalized)
                    patient?.hospitalizations = Integer.valueOf(presenter.hospitalizations)

                    repository.updateParentQuestionnaire(application, questionnaire)
                    repository.updateApplicationLocally(application)
                    navigate.postValue(R.id.action_registerInternalFragment_to_registerSchoolFragment)
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun generateSubtitle(): String {
        val treatment = if (application?.patient?.gender == GenderType.MALE.genderType)
            resourceManager.getString(R.string.male_treatment) else resourceManager.getString(R.string.female_treatment)
        val name = application?.patient?.patientName

        return resourceManager.getString(R.string.about_internal) + treatment + name
    }
}
