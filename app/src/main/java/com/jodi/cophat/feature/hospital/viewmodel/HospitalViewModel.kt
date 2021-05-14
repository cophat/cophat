package com.jodi.cophat.feature.hospital.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.HospitalConfigurePresenter
import com.jodi.cophat.data.presenter.HospitalPresenter
import com.jodi.cophat.data.presenter.ItemHospitalPresenter
import com.jodi.cophat.data.repository.HospitalRepository
import com.jodi.cophat.helper.ResourceManager
import com.jodi.cophat.helper.visibleOrGone
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HospitalViewModel(
    private val repository: HospitalRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val hospitalPresenter = MutableLiveData<HospitalPresenter>()
    val statusHospital = MutableLiveData<String>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                val list = repository.getHospitals()
                for (i in list) {
                    i.hospitalDividerVisibility = (i != list.last()).visibleOrGone()
                }

                hospitalPresenter.postValue(
                    HospitalPresenter(list.isEmpty().visibleOrGone(), list)
                )
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun getAddHospital(): HospitalConfigurePresenter {
        return HospitalConfigurePresenter(
            resourceManager.getString(R.string.add_hospital),
            resourceManager.getString(R.string.add_desc_hospital)
        )
    }

    fun getEditHospital(hospital: ItemHospitalPresenter): HospitalConfigurePresenter {
        return HospitalConfigurePresenter(
            resourceManager.getString(R.string.edit_hospital),
            resourceManager.getString(R.string.edit_desc_hospital),
            hospital.hospitalName,
            hospital.hospitalCode
        )
    }

    fun saveOrUpdateHospital(hospital: HospitalConfigurePresenter?, key: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                hospital?.let {
                    if (key != null) {
                        repository.updateHospital(it.name, it.code, key)
                        statusHospital.postValue(resourceManager.getString(R.string.success_update))
                    } else {
                        repository.saveHospital(it.name, it.code)
                        statusHospital.postValue(resourceManager.getString(R.string.success_register))
                    }
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun removeHospital(key: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                key?.let {
                    repository.removeHospital(key)
                    statusHospital.postValue(resourceManager.getString(R.string.success_remove))
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun verifyDialogPresenter(hospital: HospitalConfigurePresenter?) {
        hospital?.let {
            if (hospital.name.trim().isNotEmpty() && hospital.code.trim().isNotEmpty()) {
                isButtonEnabled.postValue(true)
            } else {
                isButtonEnabled.postValue(false)
            }
        }
    }
}