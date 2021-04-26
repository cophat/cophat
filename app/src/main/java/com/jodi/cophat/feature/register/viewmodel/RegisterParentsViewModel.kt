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
    private val repository: RegisterRepository
) : BaseViewModel() {

    val presenter = RegisterParentsPresenter()
    var application: ApplicationEntity? = null
    val navigate = MutableLiveData<Int>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                application = repository.getApplication()

                isLoading.postValue(true)
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun validatePresenter() {
        if (presenter.intervieweeName.trim().isNotEmpty() &&
            (presenter.relationshipType != RelationshipType.OTHER || presenter.relationship.trim().isNotEmpty())
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
                    val questionnaire = repository.getQuestionnaireByIdentificationCode(application.identificationCode)

                    if(application.name.equals("Não informado")){

                        questionnaire?.questionnaire?.parentApplication?.get(application.parentPosition)?.intervieweeName = presenter.intervieweeName

                        if(presenter.relationshipType.equals(RelationshipType.OTHER)){
                            questionnaire?.questionnaire?.parentApplication?.get(application.parentPosition)?.relationship = presenter.relationship
                        }else{
                            questionnaire?.questionnaire?.parentApplication?.get(application.parentPosition)?.relationship = presenter.relationshipType.relationship
                        }
                    }else{
                        questionnaire?.questionnaire?.parentApplication?.last()?.intervieweeName = presenter.intervieweeName

                        if(presenter.relationshipType.equals(RelationshipType.OTHER)){
                            questionnaire?.questionnaire?.parentApplication?.last()?.relationship = presenter.relationship
                        }else{
                            questionnaire?.questionnaire?.parentApplication?.last()?.relationship = presenter.relationshipType.relationship
                        }
                    }

                    repository.updateParentQuestionnaire(questionnaire?.questionnaire?.parentApplication!!, questionnaire)
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

}