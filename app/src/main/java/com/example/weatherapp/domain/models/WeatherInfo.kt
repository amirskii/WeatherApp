package com.example.weatherapp.domain.models

typealias WeatherMap = Map<Int, List<WeatherAtTime>>

data class WeatherInfo(
    val weatherDataPerDay: WeatherMap,
    val currentWeather: WeatherAtTime
)