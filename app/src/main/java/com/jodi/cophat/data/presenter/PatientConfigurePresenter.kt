package com.jodi.cophat.data.presenter

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.jodi.cophat.data.local.entity.*
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PatientConfigurePresenter(
    private var _title: String = "",
    private var _subtitle: String = "",
    private var _intervieweeName: String = "",
    private var _relationship: String = "",
    private var _motherProfession: String = "",
    private var _fatherProfession: String = "",
    private var _maritalStatus: String = "",
    private var _religion: String = "",
    private var _name: String = "",
    private var _medicalRecords: String = "",
    private var _identifyCode: String = "",
    private var _birthday: String = "",
    private var _age: String = "",
    private var _gender: String = "",
    private var _diagnosis: String = "",
    private var _diagnosticTime: String = "",
    private var _internedDays: String = "",
    private var _hospitalizations: String = "",
    private var _schooling: String = "",
    private var _schoolFrequency: String = "",
    private var _liveInThisCity: String = "",
    private var _address: String = "",
    private var _monthlyIncome: String = "",
    private var _educationDegree: String = "",
    private var _hospital: String = Hospital().name,
    private var _admin: String = Admin().name
) : Parcelable, BaseObservable() {
    @IgnoredOnParcel
    @get:Bindable
    var title
        get() = _title
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }

    @IgnoredOnParcel
    @get:Bindable
    var subtitle
        get() = _subtitle
        set(value) {
            _subtitle = value
            notifyPropertyChanged(BR.subtitle)
        }

    @IgnoredOnParcel
    @get:Bindable
    var intervieweeName
        get() = _intervieweeName
        set(value) {
            _intervieweeName = value
            notifyPropertyChanged(BR.intervieweeName)
        }

    @IgnoredOnParcel
    @get:Bindable
    var relationship
        get() = _relationship
        set(value) {
            _relationship = value
            notifyPropertyChanged(BR.relationship)
        }

    @IgnoredOnParcel
    @get:Bindable
    var motherProfession
        get() = _motherProfession
        set(value) {
            _motherProfession = value
            notifyPropertyChanged(BR.motherProfession)
        }

    @IgnoredOnParcel
    @get:Bindable
    var fatherProfession
        get() = _fatherProfession
        set(value) {
            _fatherProfession = value
            notifyPropertyChanged(BR.fatherProfession)
        }

    @IgnoredOnParcel
    @get:Bindable
    var maritalStatus
        get() = _maritalStatus
        set(value) {
            _maritalStatus = value
            notifyPropertyChanged(BR.maritalStatus)
        }

    @IgnoredOnParcel
    @get:Bindable
    var religion
        get() = _religion
        set(value) {
            _religion = value
            notifyPropertyChanged(BR.religion)
        }

    @IgnoredOnParcel
    @get:Bindable
    var name
        get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    @IgnoredOnParcel
    @get:Bindable
    var medicalRecords
        get() = _medicalRecords
        set(value) {
            _medicalRecords = value
            notifyPropertyChanged(BR.medicalRecords)
        }

    @IgnoredOnParcel
    @get:Bindable
    var identifyCode
        get() = _identifyCode
        set(value) {
            _identifyCode = value
            notifyPropertyChanged(BR.identifyCode)
        }

    @IgnoredOnParcel
    @get:Bindable
    var birthday
        get() = _birthday
        set(value) {
            _birthday = value
            notifyPropertyChanged(BR.birthday)
        }

    @IgnoredOnParcel
    @get:Bindable
    var age
        get() = _age
        set(value) {
            _age = value
            notifyPropertyChanged(BR.age)
        }

    @IgnoredOnParcel
    @get:Bindable
    var gender
        get() = _gender
        set(value) {
            _gender = value
            notifyPropertyChanged(BR.gender)
        }

    @IgnoredOnParcel
    @get:Bindable
    var diagnosis
        get() = _diagnosis
        set(value) {
            _diagnosis = value
            notifyPropertyChanged(BR.diagnosis)
        }

    @IgnoredOnParcel
    @get:Bindable
    var diagnosticTime
        get() = _diagnosticTime
        set(value) {
            _diagnosticTime = value
            notifyPropertyChanged(BR.diagnosticTime)
        }

    @IgnoredOnParcel
    @get:Bindable
    var internedDays
        get() = _internedDays
        set(value) {
            _internedDays = value
            notifyPropertyChanged(BR.internedDays)
        }

    @IgnoredOnParcel
    @get:Bindable
    var hospitalizations
        get() = _hospitalizations
        set(value) {
            _hospitalizations = value
            notifyPropertyChanged(BR.hospitalizations)
        }

    @IgnoredOnParcel
    @get:Bindable
    var schooling
        get() = _schooling
        set(value) {
            _schooling = value
            notifyPropertyChanged(BR.schooling)
        }

    @IgnoredOnParcel
    @get:Bindable
    var schoolFrequency
        get() = _schoolFrequency
        set(value) {
            _schoolFrequency = value
            notifyPropertyChanged(BR.schoolFrequency)
        }

    @IgnoredOnParcel
    @get:Bindable
    var liveInThisCity
        get() = _liveInThisCity
        set(value) {
            _liveInThisCity = value
            notifyPropertyChanged(BR.liveInThisCity)
        }

    @IgnoredOnParcel
    @get:Bindable
    var address
        get() = _address
        set(value) {
            _address = value
            notifyPropertyChanged(BR.address)
        }

    @IgnoredOnParcel
    @get:Bindable
    var monthlyIncome
        get() = _monthlyIncome
        set(value) {
            _monthlyIncome = value
            notifyPropertyChanged(BR.monthlyIncome)
        }

    @IgnoredOnParcel
    @get:Bindable
    var educationDegree
        get() = _educationDegree
        set(value) {
            _educationDegree = value
            notifyPropertyChanged(BR.educationDegree)
        }

    @IgnoredOnParcel
    @get:Bindable
    var admin
        get() = _admin
        set(value) {
            _admin = value
            notifyPropertyChanged(BR.admin)
        }

    @IgnoredOnParcel
    @get:Bindable
    var hospital
        get() = _hospital
        set(value) {
            _hospital = value
            notifyPropertyChanged(BR.hospital)
        }
}