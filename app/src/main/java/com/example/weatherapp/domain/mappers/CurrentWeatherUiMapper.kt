package com.example.weatherapp.domain.mappers

import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.presentation.models.CurrentWeatherUi
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class CurrentWeatherUiMapper {
    fun map(input: WeatherAtTime): CurrentWeatherUi {
        return CurrentWeatherUi(
            time = input.time.format(DateTimeFormatter.ofPattern("HH:mm")),
            iconRes = input.weatherType.iconRes,
            temperature = "${input.temperature}°C",
            weatherDesc = input.weatherType.weatherDesc,
            pressure = input.pressure.roundToInt(),
            humidity = input.humidity.roundToInt(),
            wind = input.wind.roundToInt()
        )
    }
}