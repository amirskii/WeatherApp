package com.example.weatherapp.presentation.mappers

import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.presentation.models.WeatherAtTimeUi
import java.time.LocalDateTime

class TodayWeatherUiMapper(
    private val weatherAtTimeUiMapper: WeatherAtTimeUiMapper
) {
    fun map(currentAndForecastWeather: CurrentAndForecastWeather): List<WeatherAtTimeUi> {
        return currentAndForecastWeather.days.first().hourly
            .filter { it.time >= LocalDateTime.now() }
            .let(weatherAtTimeUiMapper::mapList)
    }
}