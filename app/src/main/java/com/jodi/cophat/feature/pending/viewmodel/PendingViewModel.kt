package com.jodi.cophat.feature.pending.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.PendingPresenter
import com.jodi.cophat.data.repository.QuestionnairesRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PendingViewModel(
    private val repository: QuestionnairesRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val pendingPresenter = MutableLiveData<PendingPresenter>()
    val statusPending = MutableLiveData<String>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                val list = repository.getPendingQuestionnaires()
                for (i in list) {
                    i.pendingDividerVisibility = (i != list.last()).visibleOrGone()
                }

                pendingPresenter.postValue(
                    PendingPresenter(list.isEmpty().visibleOrGone(), list)
                )

            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

//    fun getEditPending(pending: ItemPendingPresenter): PendingConfigurePresenter {
//        return PendingConfigurePresenter(
//            resourceManager.getString(R.string.edit_admin),
//            resourceManager.getString(R.string.edit_desc_admin),
//            pending.identifyCode,
//            pending.name
//        )
//    }


    fun removePending(keyQuestionnaire: String?, position: Int?, typeInterviewee: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)
                if(typeInterviewee.equals("Paciente")){
                    repository.removePendingChild(keyQuestionnaire!!)
                }else{
                    repository.removePendingParent(keyQuestionnaire!!, position!!)
                }
                statusPending.postValue(resourceManager.getString(R.string.success_remove))
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

}