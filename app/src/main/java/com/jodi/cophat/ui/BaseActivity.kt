package com.jodi.cophat.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var permissionsRequestCode = 0

    internal lateinit var binding: T

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getViewModel(): BaseViewModel

    abstract fun initBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.importantForAutofill =
                View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        }
        binding = DataBindingUtil.setContentView(this, getLayout())

        initBinding()
    }

    fun requestPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getViewModel().hasPermission.postValue(true)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(permission),
                ++permissionsRequestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionsRequestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getViewModel().hasPermission.postValue(true)
                } else {
                    getViewModel().hasPermission.postValue(false)
                }
            }
        }
    }
}