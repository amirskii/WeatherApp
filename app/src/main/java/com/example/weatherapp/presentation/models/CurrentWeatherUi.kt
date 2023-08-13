package com.example.weatherapp.presentation.models

import androidx.annotation.DrawableRes

data class CurrentWeatherUi(
    val time: String,
    @DrawableRes val iconRes: Int,
    val temperature: String,
    val weatherDesc: String,
    val pressure: Int,
    val humidity: Int,
    val wind: Int
)