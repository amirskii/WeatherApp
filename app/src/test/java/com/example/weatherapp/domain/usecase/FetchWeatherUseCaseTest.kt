package com.example.weatherapp.domain.usecase

import com.example.weatherapp.base.BaseViewModelTest
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.domain.models.DayWeather
import com.example.weatherapp.domain.models.MapLocation
import com.example.weatherapp.domain.models.WeatherAtTime
import com.example.weatherapp.domain.models.WeatherType
import com.example.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime

class FetchWeatherUseCaseTest : BaseViewModelTest() {

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @MockK
    private lateinit var locationTracker: LocationTracker

    @Test
    fun `should call location tracker and repository`() =
        runTest {
            // given
            val interactor = initWithMocks {
                coEvery { locationTracker.getCurrentLocation() } returns
                        flowOf(mapLocation)

                coEvery { weatherRepository.getWeatherData(any(), any()) } returns
                        currentAndForecastWeather
            }

            // when
            interactor.execute()
                .toList()

            // expect
            coVerifySequence {
                locationTracker.getCurrentLocation()
                weatherRepository.getWeatherData(any(), any())
            }
        }

    @Test
    fun `should call repository only for distinct coordinates`() =
        runTest {
            // given
            val interactor = initWithMocks {
                coEvery { locationTracker.getCurrentLocation() } returns
                        flowOf(
                            mapLocation,
                            mapLocation.copy(),
                            mapLocation.copy(),
                            mapLocation.copy(),
                            mapLocation.copy(longitude = 50.1)
                        )

                coEvery { weatherRepository.getWeatherData(any(), any()) } returns
                        currentAndForecastWeather
            }

            // when
            interactor.execute()
                .toList()

            // expect
            coVerify(exactly = 2) {
                weatherRepository.getWeatherData(any(), any())
            }
        }

    private fun initWithMocks(mockBlock: () -> Unit): FetchWeatherUseCaseImpl {
        mockBlock()
        return FetchWeatherUseCaseImpl(
            weatherRepository,
            locationTracker
        )
    }

    private val mapLocation = MapLocation(
        latitude = 0.0,
        longitude = 0.0,
        bearing = 0f
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