package com.jodi.cophat.feature.generate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.Admin
import com.jodi.cophat.data.local.entity.Hospital
import com.jodi.cophat.data.local.entity.Patient
import com.jodi.cophat.data.presenter.GenerateCodePresenter
import com.jodi.cophat.data.repository.GenerateCodeRepository
import com.jodi.cophat.helper.toString
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GenerateCodeViewModel(private val repository: GenerateCodeRepository) : BaseViewModel() {

    val admins = MutableLiveData<List<Admin>>()
    val hospitals = MutableLiveData<List<Hospital>>()
    val presenter = GenerateCodePresenter()
    val navigate = MutableLiveData<Int>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                hospitals.postValue(repository.getHospitals())
                admins.postValue(repository.getAdmins())
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun validatePresenter() {
        if (presenter.child.trim().isNotEmpty() &&
            presenter.admin.name.trim().isNotEmpty() &&
            presenter.hospital.name.trim().isNotEmpty()
        ) {
            isButtonEnabled.postValue(true)
        } else {
            isButtonEnabled.postValue(false)
        }
    }

    fun initiateQuestionnaire() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                val familyId = generateFamilyId()
                val application = generateApplicationEntity(familyId)
                val questionnaire = repository.getQuestionnaireByFamilyId(familyId)
                if (questionnaire == null) {
                    if (isChildren) {
                        repository.addChildQuestionnaire(
                            familyId,
                            presenter.hospital.name,
                            application
                        )
                    } else {
                        repository.addParentQuestionnaire(familyId,
                            presenter.hospital.name,
                            application)
                    }
                    repository.saveApplicationLocally(application)
                } else {
                    if (isChildren) {
                        questionnaire.questionnaire.childApplication?.let {
                            it.apply {
                                it.familyId = questionnaire.questionnaire.familyId
                            }
                            repository.saveApplicationLocally(it)
                        }
                    } else {
                        questionnaire.questionnaire.parentApplication?.let {
                            it.apply {
                                it.familyId = questionnaire.questionnaire.familyId
                            }
                            repository.saveApplicationLocally(it)
                        }
                    }
                }
                chooseDestination()
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun generateFamilyId(): String {
        val childInitials = presenter.child
            .split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .reduce { acc, s -> acc + s }

        val nowFormatted = Calendar.getInstance().toString("ddMMyyyy")

        return childInitials + nowFormatted + presenter.hospital.code
    }

    private fun generateApplicationEntity(familyId: String): ApplicationEntity {
        return ApplicationEntity(
            familyId = familyId,
            patient = generatePatient(),
            admin = presenter.admin.name,
            date = Calendar.getInstance().toString("dd/MM/yyyy"),
            startHour = Calendar.getInstance().timeInMillis
        )
    }

    private fun generatePatient(): Patient {
        return Patient(
            patientName = presenter.child,
            gender = presenter.gender.genderType
        )
    }

    private fun chooseDestination() {
        if (isChildren) {
            navigate.postValue(R.id.action_generateCodeFragment_to_tutorialFragment)
        } else {
            navigate.postValue(R.id.action_generateCodeFragment_to_nav_register)
        }
    }

    suspend fun getPatientName(): String {
        return try {
            isLoading.postValue(true)

            repository.getPatientName() ?: ""
        } catch (e: DatabaseException) {
            handleError.postValue(e)
            ""
        } finally {
            isLoading.postValue(false)
        }
    }
}