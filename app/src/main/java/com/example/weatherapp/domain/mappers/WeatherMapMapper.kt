package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.domain.models.WeatherMap
import com.example.weatherapp.domain.models.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherMapMapper {
    private data class IndexedWeatherData(
        val index: Int,
        val data: WeatherAtTime
    )

    fun map(input: WeatherDataDto): WeatherMap {
        val output = input.time.mapIndexed { index, time ->
            val temperature = input.temperatures[index]
            val weatherCode = input.weatherCodes[index]
            val wind = input.windSpeeds[index]
            val pressure = input.pressures[index]
            val humidity = input.humidities[index]
            IndexedWeatherData(
                index = index,
                data = WeatherAtTime(
                    time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                    temperature = temperature,
                    pressure = pressure,
                    wind = wind,
                    humidity = humidity,
                    weatherType = WeatherType.fromWMO(weatherCode)
                )
            )
        }.groupBy {
            it.index / 24
        }.mapValues {
            it.value.map { it.data }
        }
        return output
    }
}