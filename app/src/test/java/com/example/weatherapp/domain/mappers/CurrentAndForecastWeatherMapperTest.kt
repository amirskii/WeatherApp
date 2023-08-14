package com.example.weatherapp.domain.mappers

import com.example.weatherapp.base.BaseTest
import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.data.remote.WeatherDto
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.domain.models.DayWeather
import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.domain.models.WeatherType
import io.kotest.extensions.time.withConstantNow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Test
import java.time.LocalDateTime

class CurrentAndForecastWeatherMapperTest: BaseTest() {

    private lateinit var mapper: CurrentAndForecastWeatherMapper

    @MockK
    private lateinit var weatherDaysMapper: WeatherDaysMapper

    override fun setup() {
        super.setup()
        every {
            weatherDaysMapper.map(weatherDataDto)
        } returns weatherDays



        mapper = CurrentAndForecastWeatherMapper(weatherDaysMapper)
    }

    @Test
    fun `should map dto to domain model`() {
        val foreverNow = LocalDateTime.parse("2023-08-13T00:00")

        withConstantNow(foreverNow) {
            LocalDateTime.now() shouldBe foreverNow

            mapper.map(weatherDto)
                .shouldBe(currentAndForecastWeather)
        }

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

    private val weatherDto = WeatherDto(
        weatherData = weatherDataDto
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

    private val currentAndForecastWeather = CurrentAndForecastWeather(
        days = weatherDays,
        currentWeather = weatherDays.first().hourly.first()
    )
}