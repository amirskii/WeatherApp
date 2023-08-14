package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class FetchWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
) : FetchWeatherUseCase {

    override suspend fun execute(): Flow<CurrentAndForecastWeather> =
            locationTracker.getCurrentLocation()
                .distinctUntilChanged()
                .flatMapLatest { location ->
                    flowOf(
                        weatherRepository.getWeatherData(
                            location.latitude,
                            location.longitude
                        )
                    )
        }.flowOn(Dispatchers.IO)
}