package com.jodi.cophat.feature.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.data.local.entity.RelationshipType
import com.jodi.cophat.data.local.entity.ReligionType
import com.jodi.cophat.data.presenter.RegisterParentsPresenter
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterParentsViewModel(
    private val repository: RegisterRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val presenter = RegisterParentsPresenter()
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
        if (presenter.intervieweeName.trim().isNotEmpty() &&
            presenter.relationshipType != RelationshipType.OTHER || presenter.relationship.trim().isNotEmpty() &&
            presenter.motherProfession.trim().isNotEmpty() &&
            presenter.fatherProfession.trim().isNotEmpty() &&
            (presenter.religionType != ReligionType.OTHER || presenter.religion.trim().isNotEmpty())
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
                    patient?.intervieweeName = presenter.intervieweeName
                    patient?.relationship = if (presenter.relationshipType != RelationshipType.OTHER)
                        presenter.relationshipType.relationship else presenter.relationship
                    patient?.motherProfession = presenter.motherProfession
                    patient?.fatherProfession = presenter.fatherProfession
                    patient?.maritalStatus = presenter.maritalStatus.maritalStatus
                    patient?.religion = if (presenter.religionType != ReligionType.OTHER)
                        presenter.religionType.religion else presenter.religion

                    repository.updateParentQuestionnaire(application, questionnaire)
                    repository.updateApplicationLocally(application)
                    navigate.postValue(R.id.action_registerParentsFragment_to_registerPatientFragment)
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

        return resourceManager.getString(R.string.patient_parents) + treatment + name
    }
}