package com.jodi.cophat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jodi.cophat.helper.hideKeyboard

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private lateinit var baseObserver: BaseObserver

    internal lateinit var binding: T

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getViewModel(): BaseViewModel

    abstract fun initBinding()

    fun setViews(vararg views: View) {
        baseObserver.setViews(views)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseObserver = BaseObserver(getViewModel(), activity?.supportFragmentManager)
        baseObserver.observeChanges(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.lifecycleOwner = this
        binding.root.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) v.hideKeyboard() }

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }
}