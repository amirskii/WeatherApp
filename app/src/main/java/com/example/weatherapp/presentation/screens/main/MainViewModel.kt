package com.example.weatherapp.presentation.screens.main

import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface MainViewModel {
    val uiState: MainUiState
    val events: Flow<MainEvents>
    fun fetchWeather()
}

data class MainUiState(
    val weatherInfo: WeatherInfo? = null,
    val loading: Boolean = false,
    val error: String? = null
)

sealed class MainEvents {
    data class ErrorEvent(val message: String) : MainEvents()
}

