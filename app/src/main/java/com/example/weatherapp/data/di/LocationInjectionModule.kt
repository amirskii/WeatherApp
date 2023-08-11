package com.example.weatherapp.data.di

import com.example.weatherapp.data.location.AndroidLocationTracker
import com.example.weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object LocationInjectionModule {

    val module = module {

        factory<LocationTracker> {
            AndroidLocationTracker(
                client = get(),
                application = get()
            )
        }

        single<FusedLocationProviderClient> {
            LocationServices.getFusedLocationProviderClient(
                androidContext()
            )
        }
    }
}