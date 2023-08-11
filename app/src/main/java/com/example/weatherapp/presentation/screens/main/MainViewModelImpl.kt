package com.example.weatherapp.presentation.screens.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModelImpl(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : MainViewModel, ViewModel() {

    override var uiState by mutableStateOf(MainUiState())

    private val eventsChannel = Channel<MainEvents>()
    override val events: Flow<MainEvents> = eventsChannel.receiveAsFlow()

    init {
        fetchWeather()
    }

    override fun fetchWeather() {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true, error = null)

            locationTracker.getCurrentLocation()
                .distinctUntilChanged()
                .debounce(TimeUnit.SECONDS.toMillis(2))
                .flatMapLatest { location ->
                    flowOf(
                        weatherRepository.getWeatherData(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
                .onEach {
                    when(it) {
                        is Resource.Success -> {
                            uiState.copy(
                                weatherInfo = it.data,
                                loading = false,
                                error = null
                            )
                            Log.d("1111", "${it.data}")
                        }
                        is Resource.Error -> {
                            uiState.copy(
                                loading = false,
                                error = it.message
                            )
                            Log.d("1111", "erorr ${it.message}")
                        }
                    }
                }
                .collect()
        }
    }

}