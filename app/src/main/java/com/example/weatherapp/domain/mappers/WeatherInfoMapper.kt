package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.remote.WeatherDto
import com.example.weatherapp.domain.models.WeatherInfo
import java.time.LocalDateTime

class WeatherInfoMapper(
    private val weatherMapMapper: WeatherMapMapper
) {
    fun map(input: WeatherDto): WeatherInfo {
        val weatherDays = weatherMapMapper.map(input.weatherData)
        val currentWeather = weatherDays.first().hourly.find {
            it.time.hour == LocalDateTime.now().hour
        }!!

        return WeatherInfo(
            days = weatherDays,
            currentWeather = currentWeather
        )
    }
}