package com.jodi.cophat

import android.app.Application
import com.jodi.cophat.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CophatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CophatApplication)
            androidFileProperties()
            modules(listOf(networkModule, dataModule, appModule, viewModelModule, repositoryModule))
        }
    }
}