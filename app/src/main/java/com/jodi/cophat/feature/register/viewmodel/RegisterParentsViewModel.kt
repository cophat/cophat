// Apagar?

package com.jodi.cophat.feature.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.data.presenter.ItemPatientPresenter
import com.jodi.cophat.data.presenter.RegisterParentsPresenter
import com.jodi.cophat.data.repository.PatientRepository
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterParentsViewModel(
    private val repository: RegisterRepository,
    private val resourceManager: ResourceManager,
    private val patientRepository: PatientRepository
) : BaseViewModel() {

    val presenter = RegisterParentsPresenter()
    var application: ApplicationEntity? = null
    val navigate = MutableLiveData<Int>()
    lateinit var patient : List<ItemPatientPresenter>

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                patient = patientRepository.getPatients()
                application = repository.getApplication()

            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun validatePresenter() {
        if (presenter.intervieweeName.trim().isNotEmpty() &&
            presenter.relationshipType != RelationshipType.OTHER || presenter.relationship.trim().isNotEmpty()
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

//                application?.let { application ->
//                    val questionnaire = repository.getQuestionnaireByFamilyId(application.familyId)

                    // Apagar?
                    val patient = patient.get(patient.size.minus(1))
                    patient?.patientIntervieweeName = presenter.intervieweeName
                    patient?.patientRelationship = if (presenter.relationshipType != RelationshipType.OTHER)
                        presenter.relationshipType.relationship else presenter.relationship

//                    repository.updateParentQuestionnaire(application, questionnaire)
//                    repository.updateApplicationLocally(application)

                    // Apagar?
//                    patientRepository.updatePatient(patient.patientIntervieweeName, patient.patientRelationship, patient.patientMotherProfession,
//                        patient.patientFatherProfession, patient.patientMaritalStatus, patient.patientReligion, patient.patientName, patient.patientMedicalRecords,
//                        patient.patientIdentifyCode, patient.patientBirthday, patient.patientAge, patient.patientGender, patient.patientDiagnosis, patient.patientDiagnosticTime,
//                        patient.patientInternedDays, patient.patientHospitalizations, patient.patientSchooling, patient.patientSchoolFrequency, patient.patientLiveInThisCity,
//                        patient.patientHome, patient.patientMonthlyIncome, patient.patientEducationDegree, patient.patientAdmin, patient.patientHospital, patient.patientFirebaseKey)

                    navigate.postValue(R.id.action_registerParentsFragment_to_registerPatientFragment)
//                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

//    private fun generateSubtitle(): String {
////        val treatment = if (patient.get(patient.size.minus(1)).patientGender == GenderType.MALE.genderType)
////            resourceManager.getString(R.string.male_treatment) else resourceManager.getString(R.string.female_treatment)
////        val name = patient.get(patient.size.minus(1)).patientName
//        val treatment = resourceManager.getString(R.string.male_treatment)
//        val name = ""
//
//        return resourceManager.getString(R.string.patient_parents) + treatment + name
//    }
}