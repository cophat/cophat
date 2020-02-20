package com.jodi.cophat.data.presenter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.jodi.cophat.data.local.entity.EducationType
import com.jodi.cophat.data.local.entity.SchoolingType

data class RegisterSchoolPresenter(
    private var _subtitle: String = "",
    private var _schooling: SchoolingType = SchoolingType.PRE,
    private var _outYes: Boolean = true,
    private var _outNo: Boolean = false,
    private var _residentYes: Boolean = true,
    private var _residentNo: Boolean = false,
    private var _address: String = "",
    private var _income: String = "",
    private var _education: EducationType = EducationType.ILLITERATE
) : BaseObservable() {

    @get:Bindable
    var subtitle
        get() = _subtitle
        set(value) {
            _subtitle = value
            notifyPropertyChanged(BR.subtitle)
        }

    @get:Bindable
    var schooling
        get() = _schooling
        set(value) {
            _schooling = value
            notifyPropertyChanged(BR.schooling)
        }

    @get:Bindable
    var outYes
        get() = _outYes
        set(value) {
            _outYes = value
            notifyPropertyChanged(BR.outYes)
        }

    @get:Bindable
    var outNo
        get() = _outNo
        set(value) {
            _outNo = value
            notifyPropertyChanged(BR.outNo)
        }

    @get:Bindable
    var residentYes
        get() = _residentYes
        set(value) {
            _residentYes = value
            notifyPropertyChanged(BR.residentYes)
        }

    @get:Bindable
    var residentNo
        get() = _residentNo
        set(value) {
            _residentNo = value
            notifyPropertyChanged(BR.residentNo)
        }

    @get:Bindable
    var address
        get() = _address
        set(value) {
            _address = value
            notifyPropertyChanged(BR.address)
        }

    @get:Bindable
    var income
        get() = _income
        set(value) {
            _income = value
            notifyPropertyChanged(BR.income)
        }

    @get:Bindable
    var education
        get() = _education
        set(value) {
            _education = value
            notifyPropertyChanged(BR.education)
        }
}