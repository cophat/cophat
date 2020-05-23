package com.jodi.cophat.feature.generate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.ApplicationEntity
import com.jodi.cophat.data.local.entity.Admin
import com.jodi.cophat.data.local.entity.Hospital
import com.jodi.cophat.data.presenter.GenerateCodePresenter
import com.jodi.cophat.data.repository.GenerateCodeRepository
import com.jodi.cophat.data.repository.PatientRepository
import com.jodi.cophat.helper.toString
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GenerateCodeViewModel(private val repository: GenerateCodeRepository, private val patientRepository: PatientRepository) : BaseViewModel() {

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
        if (presenter.identifyCode.trim().isNotEmpty() &&
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

                val identifyCode = presenter.identifyCode // Apagar? Antes era o familyId
                val application = generateApplicationEntity(identifyCode)
                val questionnaire = repository.getQuestionnaireByFamilyId(identifyCode)
                if (questionnaire == null) {
                    if (isChildren) {
                        repository.addChildQuestionnaire(
                            application
                        )
                    } else {
                        repository.addParentQuestionnaire(
                            application)
                    }
                    repository.saveApplicationLocally(application)
                } else {
                    if (isChildren) {
                        questionnaire.questionnaire.childApplication?.let {
                            it.apply {
                                it.identifyCode = questionnaire.questionnaire.childApplication?.identifyCode
                            }
                            repository.saveApplicationLocally(it)
                        }
                    } else {
                        questionnaire.questionnaire.parentApplication?.let {
                            it.apply {
                                it.identifyCode = questionnaire.questionnaire.parentApplication?.identifyCode
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

    private fun generateApplicationEntity(identifyCode: String): ApplicationEntity {
        return ApplicationEntity(
            identifyCode = identifyCode,
            admin = presenter.admin.name,
            hospital = presenter.hospital.name,
            date = Calendar.getInstance().toString("dd/MM/yyyy"),
            startHour = Calendar.getInstance().timeInMillis
        )
    }

    private fun chooseDestination() {
        if (isChildren) {
            navigate.postValue(R.id.action_generateCodeFragment_to_tutorialFragment)
        } else {
            navigate.postValue(R.id.action_generateCodeFragment_to_nav_register)
        }
    }

}