package com.nicolas.weatherapp.utils


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.nicolas.weatherapp.R
import com.nicolas.weatherapp.domain.models.Condition
import com.nicolas.weatherapp.domain.models.CurrentWeather
import com.nicolas.weatherapp.domain.models.Day
import com.nicolas.weatherapp.domain.models.Forecast
import com.nicolas.weatherapp.domain.models.ForecastDay
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


fun String.toLocation(): Location {
    return Gson().fromJson(this, Location::class.java)
}

@Composable
fun dummyLocation(): List<Location> {
    val mockProduct = LocalContext.current.resources.openRawResource(R.raw.mock_location)
        .bufferedReader().use { it.readText() }

    return List(3) { mockProduct.toLocation() }
}

@Composable
fun dummyNavController() = rememberNavController()

val dummyForecast = WeatherForecast(
    location = Location(name = "New York", country = "USA"),
    current = CurrentWeather(
        temp_c = 22.555,
        condition = Condition(
            text = "Clear",
            icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
        )
    ),
    forecast = Forecast(
        forecastday = listOf(
            ForecastDay(
                date = "2024-09-01",
                day = Day(
                    avgtemp_c = 20.4140,
                    condition = Condition(
                        text = "Sunny",
                        icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                    )
                )
            ),
            ForecastDay(
                date = "2024-09-02",
                day = Day(
                    avgtemp_c = 19.111111111,
                    condition = Condition(
                        text = "Partly Cloudy",
                        icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
                    )
                )
            ),
            ForecastDay(
                date = "2024-09-03",
                day = Day(
                    avgtemp_c = 18.0,
                    condition = Condition(
                        text = "Cloudy",
                        icon = "//cdn.weatherapi.com/weather/64x64/day/119.png"
                    )
                )
            )
        )
    )
)
