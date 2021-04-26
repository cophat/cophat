package com.jodi.cophat

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
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
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
//            val ref = FirebaseDatabase.getInstance().getReference("src/main/java/com/jodi/cophat/data/local/entity")
//            ref.keepSynced(true)
        }
    }
}