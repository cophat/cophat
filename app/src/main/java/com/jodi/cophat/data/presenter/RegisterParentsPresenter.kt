package com.jodi.cophat.data.presenter

import android.provider.ContactsContract
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.jodi.cophat.data.local.entity.MaritalStatusType
import com.jodi.cophat.data.local.entity.RelationshipType
import com.jodi.cophat.data.local.entity.ReligionType

data class RegisterParentsPresenter(
    private var _intervieweeName: String = "",
    private var _relationshipType: RelationshipType = RelationshipType.OTHER,
    private var _relationship: String = "",
    private var _gender: String = ""

) : BaseObservable() {

    @get:Bindable
    var intervieweeName
        get() = _intervieweeName
        set(value) {
            _intervieweeName = value
            notifyPropertyChanged(BR.intervieweeName)
        }

    @get:Bindable
    var relationshipType
        get() = _relationshipType
        set(value) {
            _relationshipType = value
            notifyPropertyChanged(BR.relationshipType)
        }

    @get:Bindable
    var relationship
        get() = _relationship
        set(value) {
            _relationship = value
            notifyPropertyChanged(BR.relationship)
        }
}