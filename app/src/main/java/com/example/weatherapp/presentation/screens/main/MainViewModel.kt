package com.example.weatherapp.presentation.screens.main

import com.example.weatherapp.domain.models.WeatherInfo
import com.example.weatherapp.presentation.models.CurrentWeatherUi

interface MainViewModel {
    val uiState: MainUiState
    fun permissionsAreGranted()
}

data class MainUiState(
    val weatherInfo: WeatherInfo? = null,
    val currentWeatherUi: CurrentWeatherUi? = null,
    val loading: Boolean = false,
    val error: String? = null
)

