package com.jodi.cophat.data.presenter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.jodi.cophat.data.local.entity.AnswerType

data class QuestionsPresenter(
    private var _code: String? = "",
    private var _state: String? = "",
    private var _progress: Int = 0,
    private var _statement: String? = "",
    private var _answer: AnswerType? = AnswerType.ALWAYS
) : BaseObservable() {

    @get:Bindable
    var code
        get() = _code
        set(value) {
            _code = value
            notifyPropertyChanged(BR.code)
        }

    @get:Bindable
    var progress
        get() = _progress
        set(value) {
            _progress = value
            notifyPropertyChanged(BR.progress)
        }

    @get:Bindable
    var state
        get() = _state
        set(value) {
            _state = value
            notifyPropertyChanged(BR.state)
        }

    @get:Bindable
    var statement
        get() = _statement
        set(value) {
            _statement = value
            notifyPropertyChanged(BR.statement)
        }

    @get:Bindable
    var answer
        get() = _answer
        set(value) {
            _answer = value
            notifyPropertyChanged(BR.answer)
        }
}