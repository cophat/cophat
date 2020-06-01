package com.jodi.cophat.data.presenter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.jodi.cophat.data.local.entity.Admin
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.data.local.entity.Hospital

data class GenerateCodePresenter(
    private var _identifyCode: String = "",
    private var _gender: String = "",
    private var _hospital: Hospital = Hospital(),
    private var _admin: Admin = Admin()
) : BaseObservable() {
    @get:Bindable
    var identifyCode
        get() = _identifyCode
        set(value) {
            _identifyCode = value
            notifyPropertyChanged(BR.identifyCode)
        }

    @get:Bindable
    var gender
        get() = _gender
        set(value) {
            _gender = value
            notifyPropertyChanged(BR.gender)
        }

    @get:Bindable
    var hospital
        get() = _hospital
        set(value) {
            _hospital = value
            notifyPropertyChanged(BR.hospital)
        }

    @get:Bindable
    var admin
        get() = _admin
        set(value) {
            _admin = value
            notifyPropertyChanged(BR.admin)
        }
}