package com.example.weatherapp.presentation.di

import com.example.weatherapp.domain.mappers.TodayWeatherUiMapper
import com.example.weatherapp.domain.mappers.WeatherAtTimeUiMapper
import com.example.weatherapp.presentation.screens.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainInjectionModule {

    val module = module {

        viewModel {
            MainViewModelImpl(
                weatherRepository = get(),
                locationTracker = get(),
                weatherAtTimeUiMapper = get(),
                todayWeatherUiMapper = get()
            )
        }

        factory {
            WeatherAtTimeUiMapper()
        }

        factory {
            TodayWeatherUiMapper(weatherAtTimeUiMapper = get())
        }
    }
}