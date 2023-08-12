package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): WeatherInfo
}