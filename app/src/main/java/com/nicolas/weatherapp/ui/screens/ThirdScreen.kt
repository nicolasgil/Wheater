package com.nicolas.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolas.weatherapp.R

@Composable
fun SevenDaysForecastScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_light))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "7-Days Forecast",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherDayCard("Mon", "19°C", R.drawable.ic_launcher_foreground)
            WeatherDayCard("Tue", "18°C", R.drawable.ic_launcher_foreground)
            WeatherDayCard("Wed", "18°C", R.drawable.ic_launcher_foreground)
            WeatherDayCard("Thu", "19°C", R.drawable.ic_launcher_foreground)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Calidad del Aire y UV Index
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoCard(title = "Air Quality", value = "3-Low Health Risk")
            InfoCard(title = "UV Index", value = "4 Moderate")
        }
    }
}

@Composable
fun WeatherDayCard(day: String, temp: String, icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = day, color = Color.White)
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(50.dp)
        )
        Text(text = temp, color = Color.White)
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.teal_dark), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = title, color = Color.White, fontSize = 16.sp)
        Text(text = value, color = Color.White, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSevenDaysForecastScreen() {
    SevenDaysForecastScreen()
}
