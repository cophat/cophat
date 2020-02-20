package com.matheus.cophat.feature.configure.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseException
import com.matheus.cophat.R
import com.matheus.cophat.data.presenter.AdminConfigurePresenter
import com.matheus.cophat.data.presenter.AdminPresenter
import com.matheus.cophat.data.presenter.ItemAdminPresenter
import com.matheus.cophat.data.repository.ConfigureRepository
import com.matheus.cophat.helper.ResourceManager
import com.matheus.cophat.helper.isValidEmail
import com.matheus.cophat.helper.visibleOrGone
import com.matheus.cophat.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfigureViewModel(
    private val repository: ConfigureRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    val adminPresenter = MutableLiveData<AdminPresenter>()
    val statusAdmin = MutableLiveData<String>()

    override fun initialize() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                val list = repository.getAdmins()
                for (i in list) {
                    i.adminDividerVisibility = (i != list.last()).visibleOrGone()
                }

                adminPresenter.postValue(
                    AdminPresenter(list.isEmpty().visibleOrGone(), list)
                )
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun getAddAdmin(): AdminConfigurePresenter {
        return AdminConfigurePresenter(
            resourceManager.getString(R.string.add_admin),
            resourceManager.getString(R.string.add_desc_admin)
        )
    }

    fun getEditAdmin(admin: ItemAdminPresenter): AdminConfigurePresenter {
        return AdminConfigurePresenter(
            resourceManager.getString(R.string.edit_admin),
            resourceManager.getString(R.string.edit_desc_admin),
            admin.adminName,
            admin.adminContact
        )
    }

    fun saveOrUpdateAdmin(admin: AdminConfigurePresenter?, key: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                admin?.let {
                    if (key != null) {
                        repository.updateAdmin(it.name, it.contact, key)
                        statusAdmin.postValue(resourceManager.getString(R.string.success_update))
                    } else {
                        repository.saveAdmin(it.name, it.contact)
                        statusAdmin.postValue(resourceManager.getString(R.string.success_register))
                    }
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun removeAdmin(key: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                isLoading.postValue(true)

                key?.let {
                    repository.removeAdmin(key)
                    statusAdmin.postValue(resourceManager.getString(R.string.success_remove))
                }
            } catch (e: DatabaseException) {
                handleError.postValue(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun verifyDialogPresenter(admin: AdminConfigurePresenter?) {
        admin?.let {
            if (admin.name.trim().isNotEmpty() && admin.contact.isValidEmail()) {
                isButtonEnabled.postValue(true)
            } else {
                isButtonEnabled.postValue(false)
            }
        }
    }
}