package com.matheus.cophat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.matheus.cophat.R
import com.matheus.cophat.helper.hideKeyboard

abstract class BaseDialog<T : ViewDataBinding> : DialogFragment() {

    private lateinit var baseObserver: BaseObserver

    internal lateinit var binding: T

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getViewModel(): BaseViewModel

    abstract fun getDialogTag(): String

    abstract fun initBinding()

    fun setViews(vararg views: View) {
        baseObserver.setViews(views)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseObserver = BaseObserver(getViewModel(), activity?.supportFragmentManager)
        baseObserver.observeChanges(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.let { dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(it, R.drawable.bg_gradient)) }
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.lifecycleOwner = this
        binding.root.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) v.hideKeyboard() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    fun show(fragmentManager: FragmentManager) {
        val old = fragmentManager.findFragmentByTag(getDialogTag())
        if (old != null && old.isAdded) {
            return
        }
        val ft = fragmentManager.beginTransaction()
        ft.add(this, getDialogTag())
        ft.commit()
    }
}