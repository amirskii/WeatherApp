package com.example.weatherapp.data.repository

import com.example.weatherapp.base.BaseViewModelTest
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.mappers.CurrentAndForecastWeatherMapper
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class WeatherRepositoryTest : BaseViewModelTest() {

    @MockK
    private lateinit var api: WeatherApi

    @MockK
    private lateinit var currentAndForecastWeatherMapper: CurrentAndForecastWeatherMapper

    @Test
    fun `getWeatherData should call api and mapper`() =
        runTest {
            // given
            val repository = initWithMocks {
                coEvery { api.getWeatherData(any(), any()) } returns
                        mockk()
                every { currentAndForecastWeatherMapper.map(any()) } returns mockk()
            }

            // when
            repository.getWeatherData(0.0, 0.0)

            // expect
            coVerifySequence {
                api.getWeatherData(0.0, 0.0)
                currentAndForecastWeatherMapper.map(any())
            }
        }


    private fun initWithMocks(mockBlock: () -> Unit): WeatherRepositoryImpl {
        mockBlock()
        return WeatherRepositoryImpl(
            api,
            currentAndForecastWeatherMapper
        )
    }

}