package com.example.weatherapp.di

import com.example.weatherapp.data.di.LocationInjectionModule
import com.example.weatherapp.data.di.NetworkInjectionModule
import com.example.weatherapp.presentation.di.MainInjectionModule


object InjectionModules {

    val modules = listOf(
        MainInjectionModule.module,
        NetworkInjectionModule.module,
        LocationInjectionModule.module
    )
}