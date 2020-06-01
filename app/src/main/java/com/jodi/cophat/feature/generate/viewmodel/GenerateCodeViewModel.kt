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
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.helper.toString
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class GenerateCodeViewModel(
    private val repository: GenerateCodeRepository,
    private val repositoryRegister: RegisterRepository
) : BaseViewModel() {

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
            presenter.gender.trim().isNotEmpty() &&
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

                val identifyCode = presenter.identifyCode
                val application = generateApplicationEntity(identifyCode)
                val parentList: MutableList<ApplicationEntity> = arrayListOf()
                parentList.add(application)
                val questionnaire = repository.getQuestionnaireByFamilyId(identifyCode)
                if (questionnaire == null) {
                    if (isChildren) {
                        repository.addChildQuestionnaire(
                            identifyCode,
                            application
                        )
                    } else {
                        repository.addParentQuestionnaire(
                            identifyCode,
                            parentList
                        )
                    }
                    repository.saveApplicationLocally(application)
                } else {
                    if (isChildren) {

                        questionnaire.questionnaire.let {
                            it.apply {
                                it.childApplication = application
                            }
                            repositoryRegister.updateChildrenQuestionnaire(questionnaire?.questionnaire?.childApplication!!, questionnaire)
                            repository.saveApplicationLocally(it.childApplication!!)
                        }

                    } else {

                        questionnaire.questionnaire.let {
                            it.apply {
                                it.parentApplication.add(application)
                            }
                            repositoryRegister.updateParentQuestionnaire(questionnaire?.questionnaire?.parentApplication!!, questionnaire)
                            repository.saveParentApplicationLocally(it.parentApplication)
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
            gender = presenter.gender,
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