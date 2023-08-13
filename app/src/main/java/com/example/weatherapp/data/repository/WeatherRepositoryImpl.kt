package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.mappers.CurrentAndForecastWeatherMapper
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val currentAndForecastWeatherMapper: CurrentAndForecastWeatherMapper,
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): CurrentAndForecastWeather =
        currentAndForecastWeatherMapper.map(
            api.getWeatherData(
                lat = lat,
                long = long
            )
        )
}