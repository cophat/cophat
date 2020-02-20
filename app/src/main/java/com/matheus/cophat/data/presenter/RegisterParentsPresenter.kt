package com.matheus.cophat.data.presenter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.matheus.cophat.data.local.entity.MaritalStatusType
import com.matheus.cophat.data.local.entity.ReligionType

data class RegisterParentsPresenter(
    private var _subtitle: String = "",
    private var _motherProfession: String = "",
    private var _fatherProfession: String = "",
    private var _maritalStatus: MaritalStatusType = MaritalStatusType.MARRIED,
    private var _religionType: ReligionType = ReligionType.CATHOLIC,
    private var _religion: String = ""
) : BaseObservable() {

    @get:Bindable
    var subtitle
        get() = _subtitle
        set(value) {
            _subtitle = value
            notifyPropertyChanged(BR.subtitle)
        }

    @get:Bindable
    var motherProfession
        get() = _motherProfession
        set(value) {
            _motherProfession = value
            notifyPropertyChanged(BR.motherProfession)
        }

    @get:Bindable
    var fatherProfession
        get() = _fatherProfession
        set(value) {
            _fatherProfession = value
            notifyPropertyChanged(BR.fatherProfession)
        }

    @get:Bindable
    var maritalStatus
        get() = _maritalStatus
        set(value) {
            _maritalStatus = value
            notifyPropertyChanged(BR.maritalStatus)
        }

    @get:Bindable
    var religionType
        get() = _religionType
        set(value) {
            _religionType = value
            notifyPropertyChanged(BR.religionType)
        }

    @get:Bindable
    var religion
        get() = _religion
        set(value) {
            _religion = value
            notifyPropertyChanged(BR.religion)
        }
}