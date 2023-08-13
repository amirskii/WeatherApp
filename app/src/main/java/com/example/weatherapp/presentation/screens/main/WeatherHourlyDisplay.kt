package com.example.weatherapp.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.presentation.models.WeatherAtTimeUi

@Composable
fun WeatherHourlyDisplay(
    weatherAtTimeUi: WeatherAtTimeUi,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weatherAtTimeUi.time,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = weatherAtTimeUi.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = weatherAtTimeUi.temperature,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}
