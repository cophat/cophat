package com.jodi.cophat.feature.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.data.presenter.RegisterSchoolPresenter
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterSchoolViewModel(
    private val repository: RegisterRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val presenter = RegisterSchoolPresenter()
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
        if (presenter.address.trim().isNotEmpty() &&
            presenter.income.trim().isNotEmpty()
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
                    patient?.schooling = presenter.schooling.schooling
                    patient?.schoolFrequency = presenter.outYes
                    patient?.liveInThisCity = presenter.residentYes
                    patient?.home = presenter.address
                    patient?.monthlyIncome = presenter.income
                    patient?.educationDegree = presenter.education.education

                    repository.updateParentQuestionnaire(application, questionnaire)
                    repository.updateApplicationLocally(application)
                    navigate.postValue(R.id.action_registerSchoolFragment_to_nav_questions)
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

        return resourceManager.getString(R.string.finalize_register) + treatment + name
    }
}