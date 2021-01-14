package com.example.myapplication

import android.app.Application
import com.example.myapplication.module.appModule
import com.example.myapplication.module.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(dataModule, appModule))
        }
    }
}