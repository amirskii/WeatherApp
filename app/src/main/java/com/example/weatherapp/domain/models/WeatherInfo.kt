package com.example.weatherapp.domain.models

typealias WeatherMap = Map<Int, List<WeatherData>>

data class WeatherInfo(
    val weatherDataPerDay: WeatherMap,
    val currentWeather: WeatherData
)