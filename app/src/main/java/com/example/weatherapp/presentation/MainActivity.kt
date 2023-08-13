package com.example.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapp.presentation.screens.main.MainUiState
import com.example.weatherapp.presentation.screens.main.MainViewModelImpl
import com.example.weatherapp.presentation.screens.main.CurrentWeatherSection
import com.example.weatherapp.presentation.screens.main.TodayWeatherForecast
import com.example.weatherapp.presentation.theme.CarolinaBlue
import com.example.weatherapp.presentation.theme.CuriousBlue
import com.example.weatherapp.presentation.theme.WeatherAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModelImpl>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.permissionsAreGranted()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            WeatherAppTheme {
                MainScreen(viewModel.uiState)
            }
        }
    }
}

@Composable
fun MainScreen(
    state: MainUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(CarolinaBlue)
        ) {
            state.currentWeather?.let {
                CurrentWeatherSection(weatherAtTimeUi = it)
            }
            state.todayWeather?.let {
                Spacer(modifier = Modifier.height(16.dp))
                TodayWeatherForecast(
                    dayWeatherUi = it,
                    backgroundColor = CuriousBlue
                )
            }
        }
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        state.error?.let {
            Text(
                text = it,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WeatherAppTheme {
//        MainScreen()
//    }
//}