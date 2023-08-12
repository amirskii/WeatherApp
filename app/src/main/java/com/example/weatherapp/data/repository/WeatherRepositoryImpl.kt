package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.mappers.WeatherInfoMapper
import com.example.weatherapp.domain.models.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val weatherInfoMapper: WeatherInfoMapper,
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): WeatherInfo =
        weatherInfoMapper.map(
            api.getWeatherData(
                lat = lat,
                long = long
            )
        )
}