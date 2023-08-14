package com.example.weatherapp.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.presentation.models.WeatherAtTimeUi

@Composable
fun TodayWeatherRow(
    dayWeatherUi: List<WeatherAtTimeUi>,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.today), fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(content = {
            items(dayWeatherUi) {
                WeatherHourlyDisplay(
                    weatherAtTimeUi = it,
                    backgroundColor = backgroundColor,
                    modifier = Modifier
                        .height(120.dp)
                        .padding(horizontal = 8.dp)
                )
            }
        })
    }
}
