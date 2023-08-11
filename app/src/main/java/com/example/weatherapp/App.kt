package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.InjectionModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)

            modules(InjectionModules.modules)
        }
    }
}