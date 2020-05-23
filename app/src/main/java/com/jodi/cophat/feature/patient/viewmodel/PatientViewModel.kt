package com.jodi.cophat.feature.patient.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.Admin
import com.jodi.cophat.data.local.entity.Hospital
import com.jodi.cophat.data.presenter.PatientConfigurePresenter
import com.jodi.cophat.data.presenter.PatientPresenter
import com.jodi.cophat.data.presenter.ItemPatientPresenter
import com.jodi.cophat.data.repository.GenerateCodeRepository
import com.jodi.cophat.data.repository.PatientRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientViewModel(
    private val repository: PatientRepository,
    private val resourceManager: ResourceManager,
    private val repositoryCode: GenerateCodeRepository
) : BaseViewModel() {

    val patientPresenter = MutableLiveData<PatientPresenter>()
    val statusPatient = MutableLiveData<String>()
    val admins = MutableLiveData<List<Admin>>()
    val hospitals = MutableLiveData<List<Hospital>>()
    val navigate = MutableLiveData<Int>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                hospitals.postValue(repositoryCode.getHospitals())
                admins.postValue(repositoryCode.getAdmins())

                val list = repository.getPatients()
                for (i in list) {
                    i.patientDividerVisibility = (i != list.last()).visibleOrGone()
                }

                patientPresenter.postValue(
                    PatientPresenter(list.isEmpty().visibleOrGone(), list)
                )
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun getAddPatient(): PatientConfigurePresenter {
        return PatientConfigurePresenter(
            resourceManager.getString(R.string.add_patient),
            resourceManager.getString(R.string.add_desc_patient)
        )
    }

    fun getEditPatient(patient: ItemPatientPresenter): PatientConfigurePresenter {

        return PatientConfigurePresenter(
            resourceManager.getString(R.string.edit_patient),
            resourceManager.getString(R.string.edit_desc_patient),
            patient.patientIntervieweeName, patient.patientRelationship,
            patient.patientMotherProfession, patient.patientFatherProfession, patient.patientMaritalStatus,
            patient.patientReligion, patient.patientName, patient.patientMedicalRecords, patient.patientIdentifyCode,
            patient.patientBirthday, patient.patientAge.toString(), patient.patientGender, patient.patientDiagnosis,
            patient.patientDiagnosticTime.toString(), patient.patientInternedDays.toString(), patient.patientHospitalizations.toString(),
            patient.patientSchooling, patient.patientSchoolFrequency, patient.patientLiveInThisCity, patient.patientHome,
            patient.patientMonthlyIncome, patient.patientEducationDegree, patient.patientHospital, patient.patientAdmin
        )
    }

    fun saveOrUpdatePatient(patient: PatientConfigurePresenter?, key: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                patient?.let {
                    if (key != null) {
                        repository.updatePatient(it.intervieweeName, it.relationship, it.motherProfession,it.fatherProfession,
                            it.maritalStatus, it.religion, it.name, it.medicalRecords, it.identifyCode, it.birthday, it.age.toInt(), it.gender, it.diagnosis, it.diagnosticTime.toInt(),
                            it.internedDays.toInt(), it.hospitalizations.toInt(), it.schooling, it.schoolFrequency, it.liveInThisCity, it.address, it.monthlyIncome, it.educationDegree, it.hospital, it.admin, key)
                        statusPatient.postValue(resourceManager.getString(R.string.success_update))
                    } else {
                        repository.savePatient(it.intervieweeName, it.relationship, it.motherProfession,it.fatherProfession,
                            it.maritalStatus, it.religion, it.name, it.medicalRecords, it.identifyCode, it.birthday, it.age.toInt(), it.gender, it.diagnosis, it.diagnosticTime.toInt(),
                            it.internedDays.toInt(), it.hospitalizations.toInt(), it.schooling, it.schoolFrequency, it.liveInThisCity, it.address, it.monthlyIncome, it.educationDegree, it.hospital, it.admin
                        )
                        statusPatient.postValue(resourceManager.getString(R.string.success_register))
                    }
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun removePatient(key: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                key?.let {
                    repository.removePatient(key)
                    statusPatient.postValue(resourceManager.getString(R.string.success_remove))
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun verifyDialogPresenter(patient: PatientConfigurePresenter?) {
        patient?.let {
            if (patient.name.trim().isNotEmpty()) {
                isButtonEnabled.postValue(true)
            } else {
                isButtonEnabled.postValue(false)
            }
        }
    }
}