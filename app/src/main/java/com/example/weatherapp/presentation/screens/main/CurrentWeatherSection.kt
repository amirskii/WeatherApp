package com.example.weatherapp.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.presentation.models.WeatherAtTimeUi
import com.example.weatherapp.presentation.theme.LightBlue

@Composable
fun CurrentWeatherSection(
    weatherAtTimeUi: WeatherAtTimeUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Today ${weatherAtTimeUi.time}",
            modifier = Modifier.align(Alignment.End),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = weatherAtTimeUi.iconRes),
            contentDescription = null,
            modifier = Modifier.width(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = weatherAtTimeUi.temperature,
            fontSize = 50.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = weatherAtTimeUi.weatherDesc,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LightBlue, shape = RoundedCornerShape(12.dp))
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherDataDisplay(
                value = weatherAtTimeUi.pressure,
                icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
            WeatherDataDisplay(
                value = weatherAtTimeUi.humidity,
                icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
            WeatherDataDisplay(
                value = weatherAtTimeUi.wind,
                icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
        }
    }
}
