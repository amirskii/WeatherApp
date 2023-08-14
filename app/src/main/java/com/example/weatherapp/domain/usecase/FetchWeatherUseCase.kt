package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import kotlinx.coroutines.flow.Flow

interface FetchWeatherUseCase {

    suspend fun execute(): Flow<CurrentAndForecastWeather>
}