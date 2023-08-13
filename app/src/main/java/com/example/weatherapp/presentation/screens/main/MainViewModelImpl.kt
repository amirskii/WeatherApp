package com.example.weatherapp.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.mappers.TodayWeatherUiMapper
import com.example.weatherapp.domain.mappers.WeatherAtTimeUiMapper
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val weatherAtTimeUiMapper: WeatherAtTimeUiMapper,
    private val todayWeatherUiMapper: TodayWeatherUiMapper
) : MainViewModel, ViewModel() {

    override var uiState by mutableStateOf(MainUiState())
    private val hasPermission = MutableStateFlow(false)

    init {
        collectWeather()
    }

    override fun permissionsAreGranted() {
        hasPermission.value = true
    }

    private fun collectWeather() {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true, error = null)

            hasPermission.flatMapLatest { hasPermission ->
                if (hasPermission) {
                    locationTracker.getCurrentLocation()
                        .distinctUntilChanged()
                        .flatMapLatest { location ->
                            flowOf(
                                weatherRepository.getWeatherData(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        }
                } else {
                    flowOf()
                }
            }
                .catch {
                    uiState = uiState.copy(
                        loading = false,
                        error = it.message
                    )
                }
                .onEach {
                    uiState = uiState.copy(
                        currentAndForecastWeather = it,
                        currentWeather = it.currentWeather.let(weatherAtTimeUiMapper::map),
                        todayWeather = todayWeatherUiMapper.map(it),
                        loading = false,
                        error = null
                    )
                }
                .collect()
        }
    }

}