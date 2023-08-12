package com.example.weatherapp.presentation.screens.main

import com.example.weatherapp.domain.models.WeatherInfo

interface MainViewModel {
    val uiState: MainUiState
    fun permissionsAreGranted()
}

data class MainUiState(
    val weatherInfo: WeatherInfo? = null,
    val loading: Boolean = false,
    val error: String? = null
)

