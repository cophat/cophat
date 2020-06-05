package com.jodi.cophat.data.presenter

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendingConfigurePresenter(
    var _identifyCode: String = "",
    var _name: String = "",
    var _typeInterviewee: String = "",
    var _admin: String = "",
    var _hospital: String = "",
    var _date: String = "",
    var _startHour: Long = 0,
    var _parentPosition: Int = 0,
    var _keyQuestionnaire: String = "",
    var _pendingDividerVisibility: Int = 0
) : Parcelable, BaseObservable() {
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
    var name
        get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    @IgnoredOnParcel
    @get:Bindable
    var typeInterviewee
        get() = _typeInterviewee
        set(value) {
            _typeInterviewee = value
            notifyPropertyChanged(BR.typeInterviewee)
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

    @IgnoredOnParcel
    @get:Bindable
    var date
        get() = _date
        set(value) {
            _date = value
            notifyPropertyChanged(BR.date)
        }

    @IgnoredOnParcel
    @get:Bindable
    var startHour
        get() = _startHour
        set(value) {
            _startHour = value
            notifyPropertyChanged(BR.startHour)
        }

    @IgnoredOnParcel
    @get:Bindable
    var parentPosition
        get() = _parentPosition
        set(value) {
            _parentPosition = value
            notifyPropertyChanged(BR.parentPosition)
        }

    @IgnoredOnParcel
    @get:Bindable
    var keyQuestionnaire
        get() = _keyQuestionnaire
        set(value) {
            _keyQuestionnaire = value
            notifyPropertyChanged(BR.keyQuestionnaire)
        }
}