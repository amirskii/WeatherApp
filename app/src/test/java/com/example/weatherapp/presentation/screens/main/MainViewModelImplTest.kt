package com.example.weatherapp.presentation.screens.main

import com.example.weatherapp.base.BaseViewModelTest
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.domain.models.DayWeather
import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.domain.models.WeatherType
import com.example.weatherapp.domain.usecase.FetchWeatherUseCase
import com.example.weatherapp.presentation.mappers.TodayWeatherUiMapper
import com.example.weatherapp.presentation.mappers.WeatherAtTimeUiMapper
import com.example.weatherapp.presentation.models.WeatherAtTimeUi
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException
import java.time.LocalDateTime

class MainViewModelImplTest : BaseViewModelTest() {
    @MockK
    lateinit var fetchWeatherUseCase: FetchWeatherUseCase

    @MockK
    lateinit var weatherAtTimeUiMapper: WeatherAtTimeUiMapper

    @MockK
    lateinit var todayWeatherUiMapper: TodayWeatherUiMapper

    private lateinit var viewModel: MainViewModelImpl

    @Test
    fun `if permissions are granted use case and mappers should be called in right order`() =
        runTest {
            // given
            setupWithMocks()

            // when
            viewModel.permissionsAreGranted()

            // expect
            coVerifySequence {
                fetchWeatherUseCase.execute()
                weatherAtTimeUiMapper.map(any())
                todayWeatherUiMapper.map(any())
            }
        }

    @Test
    fun `if permissions are granted state should contain current weather`() =
        runTest {
            // given
            setupWithMocks()
            viewModel.permissionsAreGranted()

            // expect
            viewModel.uiState.currentWeather
                .shouldBe(weatherAtTimeUi)
        }

    @Test
    fun `if permissions are granted state should contain today weather`() =
        runTest {
            // given
            setupWithMocks()
            viewModel.permissionsAreGranted()

            // expect
            viewModel.uiState.todayWeather
                .shouldBe(listOf(weatherAtTimeUi))
        }

    @Test
    fun `if permissions are not granted error should be empty weather`() =
        runTest {
            // given
            setupWithMocks()

            // expect
            viewModel.uiState.currentWeather
                .shouldBe(null)
        }

    @Test
    fun `if there is an unknown error state should contain the same error`() =
        runTest {
            // given
            setupWithMocks(isError = true)
            viewModel.permissionsAreGranted()

            // expect
            viewModel.uiState.error
                .shouldBe("unknown error")
        }

    private fun setupWithMocks(isError: Boolean = false) {
        viewModel = MainViewModelImpl(
            fetchWeatherUseCase,
            weatherAtTimeUiMapper,
            todayWeatherUiMapper,
        )

        if (isError) {
            coEvery { fetchWeatherUseCase.execute() } returns flow {
                throw IOException()
            }
        } else {
            coEvery { fetchWeatherUseCase.execute() } returns flowOf(currentAndForecastWeather)
        }

        every { weatherAtTimeUiMapper.map(any()) } returns weatherAtTimeUi

        every {
            todayWeatherUiMapper.map(any())
        } returns listOf(weatherAtTimeUi)
    }

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

    private val weatherAtTimeUi = WeatherAtTimeUi(
        time = "time",
        iconRes = 0,
        temperature = "temperature",
        weatherDesc = "weatherDesc",
        pressure = "pressure",
        humidity = "humidity",
        wind = "wind"
    )
}