package com.matheus.cophat.data.presenter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class RegisterPatientPresenter(
    private var _subtitle: String = "",
    private var _name: String = "",
    private var _medicalRecords: String = "",
    private var _birthday: String = "",
    private var _age: String = "",
    private var _male: Boolean = true,
    private var _female: Boolean = false
) : BaseObservable() {

    @get:Bindable
    var subtitle
        get() = _subtitle
        set(value) {
            _subtitle = value
            notifyPropertyChanged(BR.subtitle)
        }

    @get:Bindable
    var name
        get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var medicalRecords
        get() = _medicalRecords
        set(value) {
            _medicalRecords = value
            notifyPropertyChanged(BR.medicalRecords)
        }

    @get:Bindable
    var birthday
        get() = _birthday
        set(value) {
            _birthday = value
            notifyPropertyChanged(BR.birthday)
        }

    @get:Bindable
    var age
        get() = _age
        set(value) {
            _age = value
            notifyPropertyChanged(BR.age)
        }

    @get:Bindable
    var male
        get() = _male
        set(value) {
            _male = value
            notifyPropertyChanged(BR.male)
        }

    @get:Bindable
    var female
        get() = _female
        set(value) {
            _female = value
            notifyPropertyChanged(BR.female)
        }
}