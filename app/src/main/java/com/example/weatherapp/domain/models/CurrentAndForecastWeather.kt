package com.example.weatherapp.domain.models

data class CurrentAndForecastWeather(
    val days: List<DayWeather>,
    val currentWeather: WeatherAtTime
)

data class DayWeather(
    val hourly: List<WeatherAtTime>
)