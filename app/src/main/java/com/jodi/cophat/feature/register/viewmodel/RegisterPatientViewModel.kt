package com.jodi.cophat.feature.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.data.presenter.RegisterPatientPresenter
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.isBeforeToday
import com.jodi.cophat.helper.isValidDate
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterPatientViewModel(
    private val repository: RegisterRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val presenter = RegisterPatientPresenter()
    var application: ApplicationEntity? = null
    val navigate = MutableLiveData<Int>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                application = repository.getApplication()

                presenter.subtitle = generateSubtitle()
                application?.patient?.let {patient ->
                    patient.patientName?.let {
                        presenter.name = it
                    }
                    patient.gender?.let {
                        presenter.male = it == GenderType.MALE.genderType
                        presenter.female = it == GenderType.FEMALE.genderType
                    }
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun validatePresenter() {
        if (presenter.name.trim().isNotEmpty() &&
           // presenter.medicalRecords.trim().isNotEmpty() &&
            presenter.birthday.trim().isValidDate("dd/MM/yyyy") &&
            presenter.birthday.trim().isBeforeToday("dd/MM/yyyy") &&
            presenter.age.trim().isNotEmpty() &&
            (presenter.male || presenter.female)
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
                    patient?.medicalRecords = presenter.medicalRecords
                    patient?.birthday = presenter.birthday
                    patient?.age = Integer.valueOf(presenter.age)

                    repository.updateParentQuestionnaire(application, questionnaire)
                    repository.updateApplicationLocally(application)
                    navigate.postValue(R.id.action_registerPatientFragment_to_registerInternalFragment)
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

        return resourceManager.getString(R.string.confirm_patient) + treatment + name
    }
}