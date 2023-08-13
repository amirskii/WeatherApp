package com.example.weatherapp.domain.mappers

import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.presentation.models.WeatherAtTimeUi
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class WeatherAtTimeUiMapper {
    fun map(input: WeatherAtTime): WeatherAtTimeUi =
        WeatherAtTimeUi(
            time = input.time.format(DateTimeFormatter.ofPattern("HH:mm")),
            iconRes = input.weatherType.iconRes,
            temperature = "${input.temperature}°C",
            weatherDesc = input.weatherType.weatherDesc,
            pressure = "${input.pressure.roundToInt()}hpa",
            humidity = "${input.humidity.roundToInt()}%",
            wind = "${input.wind.roundToInt()}km/h",
        )

    fun mapList(input: List<WeatherAtTime>): List<WeatherAtTimeUi> =
        input.map {
            map(it)
        }
}