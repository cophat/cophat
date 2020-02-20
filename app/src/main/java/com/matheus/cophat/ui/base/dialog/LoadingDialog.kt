package com.matheus.cophat.ui.base.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.matheus.cophat.R

class LoadingDialog : DialogFragment() {

    companion object {
        const val TAG = "dialog_loading"

        @Synchronized
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return AlertDialog.Builder(activity!!)
            .setView(View.inflate(context, R.layout.dialog_loading, null))
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun show(fragmentManager: FragmentManager) {
        val old = fragmentManager.findFragmentByTag(TAG)
        if (old != null && old.isAdded) {
            return
        }
        val ft = fragmentManager.beginTransaction()
        ft.add(this, TAG)
        ft.commitNow()
    }
}