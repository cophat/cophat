//// Apagar?
//
//package com.jodi.cophat.feature.register.viewmodel
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.google.firebase.database.DatabaseException
//import com.jodi.cophat.R
//import com.jodi.cophat.data.local.entity.ApplicationEntity
//import com.jodi.cophat.data.local.entity.GenderType
//import com.jodi.cophat.data.presenter.ItemPatientPresenter
//import com.jodi.cophat.data.presenter.RegisterPatientPresenter
//import com.jodi.cophat.data.repository.PatientRepository
//import com.jodi.cophat.data.repository.RegisterRepository
//import com.jodi.cophat.helper.ResourceManager
//import com.jodi.cophat.helper.isBeforeToday
//import com.jodi.cophat.helper.isValidDate
//import com.jodi.cophat.ui.BaseViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class RegisterPatientViewModel(
//    private val repository: RegisterRepository,
//    private val resourceManager: ResourceManager,
//    private val patientRepository: PatientRepository
//) : BaseViewModel() {
//
//    val presenter = RegisterPatientPresenter()
//    var application: ApplicationEntity? = null
//    val navigate = MutableLiveData<Int>()
//    lateinit var patient : List<ItemPatientPresenter>
//
//    override fun initialize() {
//        viewModelScope.launch(context = Dispatchers.IO) {
//            try {
//                isLoading.postValue(true)
//
//                patient = patientRepository.getPatients()
//                application = repository.getApplication()
//
//                patient.get(patient.size.minus(1)).let {patient ->
//                    patient.patientName?.let {
//                        presenter.name = it
//                    }
//                    patient.patientGender?.let {
//                        presenter.male = it == GenderType.MALE.genderType
//                        presenter.female = it == GenderType.FEMALE.genderType
//                    }
//                }
//            } catch (e: DatabaseException) {
//                handleError.postValue(e)
//            } finally {
//                isLoading.postValue(false)
//            }
//        }
//    }
//
//    fun validatePresenter() {
//        if (presenter.name.trim().isNotEmpty() &&
//           // presenter.medicalRecords.trim().isNotEmpty() &&
//            presenter.identifyCode.trim().isNotEmpty() &&
//            presenter.birthday.trim().isValidDate("dd/MM/yyyy") &&
//            presenter.birthday.trim().isBeforeToday("dd/MM/yyyy") &&
//            presenter.age.trim().isNotEmpty() &&
//            (presenter.male || presenter.female)
//        ) {
//            isButtonEnabled.postValue(true)
//        } else {
//            isButtonEnabled.postValue(false)
//        }
//    }
//
//    fun updateApplication() {
//        viewModelScope.launch(context = Dispatchers.IO) {
//            try {
//                isLoading.postValue(true)
//
//                application?.let { application ->
//                    val questionnaire = repository.getQuestionnaireByFamilyId(application.identifyCode)
//
//                    // Apagar?
//                    val patient = patient.get(patient.size.minus(1))
//                    patient?.patientMedicalRecords = presenter.medicalRecords
//                    patient?.patientIdentifyCode = presenter.identifyCode
//                    patient?.patientBirthday = presenter.birthday
//                    patient?.patientAge = Integer.valueOf(presenter.age)
//
//                    repository.updateParentQuestionnaire(application, questionnaire)
//                    repository.updateApplicationLocally(application)
//                    patientRepository.updatePatient(patient.patientIntervieweeName, patient.patientRelationship, patient.patientMotherProfession, patient.patientFatherProfession,
//                    patient.patientMaritalStatus, patient.patientReligion, patient.patientName, patient.patientMedicalRecords, patient.patientIdentifyCode, patient.patientBirthday,
//                    patient.patientAge, patient.patientGender, patient.patientDiagnosis, patient.patientDiagnosticTime, patient.patientInternedDays, patient.patientHospitalizations,
//                    patient.patientSchooling, patient.patientSchoolFrequency, patient.patientLiveInThisCity, patient.patientHome, patient.patientMonthlyIncome, patient.patientEducationDegree,
//                    patient.patientFirebaseKey, patient.patientAdmin, patient.patientHospital)
//                    navigate.postValue(R.id.action_registerPatientFragment_to_registerInternalFragment)
//                }
//            } catch (e: DatabaseException) {
//                handleError.postValue(e)
//            } finally {
//                isLoading.postValue(false)
//            }
//        }
//    }
//
////    private fun generateSubtitle(): String {
////        val treatment = if (patient.get(patient.size.minus(1)).patientGender == GenderType.MALE.genderType)
////            resourceManager.getString(R.string.male_treatment) else resourceManager.getString(R.string.female_treatment)
////        val name = patient.get(patient.size.minus(1)).patientName
////
////        return resourceManager.getString(R.string.confirm_patient) + treatment + name
////    }
//}