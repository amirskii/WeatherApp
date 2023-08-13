package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.domain.models.DayWeather
import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.domain.models.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDaysMapper {
    fun map(input: WeatherDataDto): List<DayWeather> {
        val days = mutableListOf<DayWeather>()
        val currentDay = mutableListOf<WeatherAtTime>()

        input.time.forEachIndexed { i, time ->
            val temperature = input.temperatures[i]
            val weatherCode = input.weatherCodes[i]
            val wind = input.windSpeeds[i]
            val pressure = input.pressures[i]
            val humidity = input.humidities[i]

            currentDay.add(
                WeatherAtTime(
                    time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                    temperature = temperature,
                    pressure = pressure,
                    wind = wind,
                    humidity = humidity,
                    weatherType = WeatherType.fromWMO(weatherCode)
                )
            )
            if (i > 0 && i.mod(23) == 0) {
                days.add(
                    DayWeather(mutableListOf<WeatherAtTime>().apply {
                        addAll(currentDay)
                    })
                )
                currentDay.clear()
            }
        }

        return days
    }
}