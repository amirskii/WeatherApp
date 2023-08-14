package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.base.BaseTest
import com.example.weatherapp.domain.models.DayWeather
import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.domain.models.WeatherType
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.LocalDateTime

class WeatherDaysMapperTest : BaseTest() {

    private lateinit var mapper: WeatherDaysMapper
    override fun setup() {
        super.setup()
        mapper = WeatherDaysMapper()
    }

    @Test
    fun `should map dto to domain model`() {
        mapper.map(weatherDataDto)
            .shouldBe(weatherDays)
    }

    private val weatherDataDto = WeatherDataDto(
        time = listOf(
            "2023-08-13T00:00",
            "2023-08-13T01:00",
        ),
        temperatures = listOf(27.4, 24.5),
        weatherCodes = listOf(0, 0),
        pressures = listOf(1013.8, 1013.9),
        windSpeeds = listOf(14.0, 14.1),
        humidities = listOf(58.0, 66.0),
    )

    private val weatherDays = listOf<DayWeather>(
        DayWeather(
            hourly = listOf(
                WeatherAtTime(
                    time = LocalDateTime.parse("2023-08-13T00:00"),
                    temperature = 27.4,
                    pressure = 1013.8,
                    wind = 14.0,
                    humidity = 58.0,
                    weatherType = WeatherType.ClearSky
                ),
                WeatherAtTime(
                    time = LocalDateTime.parse("2023-08-13T01:00"),
                    temperature = 24.5,
                    pressure = 1013.9,
                    wind = 14.1,
                    humidity = 66.0,
                    weatherType = WeatherType.ClearSky
                )
            )
        )
    )
}
