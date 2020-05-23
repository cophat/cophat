//// Apagar?
//
//package com.jodi.cophat.data.presenter
//
//import androidx.databinding.BaseObservable
//import androidx.databinding.Bindable
//import androidx.databinding.library.baseAdapters.BR
//
//data class RegisterInternalPresenter(
//    private var _subtitle: String = "",
//    private var _diagnosis: String = "",
//    private var _diagnosisTime: String = "",
//    private var _daysHospitalized: String = "",
//    private var _hospitalizations: String = ""
//) : BaseObservable() {
//
//    @get:Bindable
//    var subtitle
//        get() = _subtitle
//        set(value) {
//            _subtitle = value
//            notifyPropertyChanged(BR.subtitle)
//        }
//
//    @get:Bindable
//    var diagnosis
//        get() = _diagnosis
//        set(value) {
//            _diagnosis = value
//            notifyPropertyChanged(BR.diagnosis)
//        }
//
//    @get:Bindable
//    var diagnosisTime
//        get() = _diagnosisTime
//        set(value) {
//            _diagnosisTime = value
//            notifyPropertyChanged(BR.diagnosisTime)
//        }
//
//    @get:Bindable
//    var daysHospitalized
//        get() = _daysHospitalized
//        set(value) {
//            _daysHospitalized = value
//            notifyPropertyChanged(BR.daysHospitalized)
//        }
//
//    @get:Bindable
//    var hospitalizations
//        get() = _hospitalizations
//        set(value) {
//            _hospitalizations = value
//            notifyPropertyChanged(BR.hospitalizations)
//        }
//}