package com.matheus.cophat.di

import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.matheus.cophat.BuildConfig
import com.matheus.cophat.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, BuildConfig.DATABASE_NAME
        ).build()
    }

    single {
        get<AppDatabase>().applicationDao()
    }

    single {
        FirebaseDatabase.getInstance().reference
    }
}