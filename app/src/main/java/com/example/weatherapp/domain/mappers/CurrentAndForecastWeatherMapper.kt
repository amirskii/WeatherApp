package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.remote.WeatherDto
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import java.time.LocalDateTime

class CurrentAndForecastWeatherMapper(
    private val weatherDaysMapper: WeatherDaysMapper
) {
    fun map(input: WeatherDto): CurrentAndForecastWeather {
        val weatherDays = weatherDaysMapper.map(input.weatherData)
        val currentWeather = weatherDays.first().hourly.find {
            it.time.hour == LocalDateTime.now().hour
        }!!

        return CurrentAndForecastWeather(
            days = weatherDays,
            currentWeather = currentWeather
        )
    }
}