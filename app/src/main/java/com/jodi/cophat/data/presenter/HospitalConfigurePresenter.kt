package com.jodi.cophat.data.presenter

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HospitalConfigurePresenter(
    private var _title: String = "",
    private var _subtitle: String = "",
    private var _name: String = "",
    private var _code: String = ""
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
    var name
        get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    @IgnoredOnParcel
    @get:Bindable
    var code
        get() = _code
        set(value) {
            _code = value
            notifyPropertyChanged(BR.code)
        }
}