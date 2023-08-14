package com.example.weatherapp.presentation.di

import com.example.weatherapp.domain.usecase.FetchWeatherUseCase
import com.example.weatherapp.domain.usecase.FetchWeatherUseCaseImpl
import com.example.weatherapp.presentation.mappers.TodayWeatherUiMapper
import com.example.weatherapp.presentation.mappers.WeatherAtTimeUiMapper
import com.example.weatherapp.presentation.screens.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainInjectionModule {

    val module = module {

        factory<FetchWeatherUseCase> {
            FetchWeatherUseCaseImpl(
                weatherRepository = get(),
                locationTracker = get()
            )
        }

        viewModel {
            MainViewModelImpl(
                fetchWeatherUseCase = get(),
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