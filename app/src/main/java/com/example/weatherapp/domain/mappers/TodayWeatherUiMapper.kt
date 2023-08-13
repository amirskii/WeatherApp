package com.example.weatherapp.domain.mappers

import com.example.weatherapp.domain.models.WeatherInfo
import com.example.weatherapp.presentation.models.WeatherAtTimeUi
import java.time.LocalDateTime

class TodayWeatherUiMapper(
    private val weatherAtTimeUiMapper: WeatherAtTimeUiMapper
) {
    fun map(weatherInfo: WeatherInfo): List<WeatherAtTimeUi> {
        return weatherInfo.days.first().hourly
            .filter { it.time >= LocalDateTime.now() }
            .let(weatherAtTimeUiMapper::mapList)
    }
}