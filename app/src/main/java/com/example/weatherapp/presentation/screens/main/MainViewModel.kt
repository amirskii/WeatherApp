package com.example.weatherapp.presentation.screens.main

import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.presentation.models.WeatherAtTimeUi

interface MainViewModel {
    val uiState: MainUiState
    fun permissionsAreGranted()
}

data class MainUiState(
    val currentAndForecastWeather: CurrentAndForecastWeather? = null,
    val currentWeather: WeatherAtTimeUi? = null,
    val todayWeather: List<WeatherAtTimeUi>? = null,
    val loading: Boolean = false,
    val error: String? = null
)

