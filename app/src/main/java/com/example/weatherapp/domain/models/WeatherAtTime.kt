package com.example.weatherapp.domain.models

import java.time.LocalDateTime

data class WeatherAtTime(
    val time: LocalDateTime,
    val temperature: Double,
    val pressure: Double,
    val wind: Double,
    val humidity: Double,
    val weatherType: WeatherType
)