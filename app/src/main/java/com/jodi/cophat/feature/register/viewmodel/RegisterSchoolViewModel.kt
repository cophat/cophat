package com.jodi.cophat.feature.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.data.presenter.ItemPatientPresenter
import com.jodi.cophat.data.presenter.RegisterSchoolPresenter
import com.jodi.cophat.data.repository.PatientRepository
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterSchoolViewModel(
    private val repository: RegisterRepository,
    private val resourceManager: ResourceManager,
    private val patientRepository: PatientRepository
) : BaseViewModel() {

    val presenter = RegisterSchoolPresenter()
    var application: ApplicationEntity? = null
    val navigate = MutableLiveData<Int>()
    lateinit var patient : List<ItemPatientPresenter> // Apagar?

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                patient = patientRepository.getPatients()
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

                    // Apagar?
                    val patient = patient.get(patient.size.minus(1))
                    patient.patientSchooling = presenter.schooling.schooling
                    patient.patientSchoolFrequency = presenter.outYes.toString()
                    patient.patientLiveInThisCity = presenter.residentYes.toString()
                    patient.patientHome = presenter.address
                    patient.patientMonthlyIncome = presenter.income
                    patient.patientEducationDegree = presenter.education.education

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
        val treatment = if (patient.get(patient.size.minus(1)).patientGender == GenderType.MALE.genderType)
            resourceManager.getString(R.string.male_treatment) else resourceManager.getString(R.string.female_treatment)
        val name = patient.get(patient.size.minus(1)).patientName

        return resourceManager.getString(R.string.finalize_register) + treatment + name
    }
}