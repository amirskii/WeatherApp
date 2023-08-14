package com.example.weatherapp.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.models.CurrentAndForecastWeather
import com.example.weatherapp.domain.usecase.FetchWeatherUseCase
import com.example.weatherapp.presentation.mappers.TodayWeatherUiMapper
import com.example.weatherapp.presentation.mappers.WeatherAtTimeUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
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
                    fetchWeatherUseCase.execute()
                } else {
                    emptyFlow<CurrentAndForecastWeather>()
                }
            }
                .catch {
                    uiState = uiState.copy(
                        loading = false,
                        error = it.message ?: "unknown error"
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