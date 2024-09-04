package com.nicolas.weatherapp.utils


import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.nicolas.weatherapp.domain.models.Condition
import com.nicolas.weatherapp.domain.models.CurrentWeather
import com.nicolas.weatherapp.domain.models.Day
import com.nicolas.weatherapp.domain.models.Forecast
import com.nicolas.weatherapp.domain.models.ForecastDay
import com.nicolas.weatherapp.domain.models.Hour
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast


@Composable
fun dummyNavController() = rememberNavController()


data class WeatherForecastObject(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast
) {
    companion object {

        val EMPTY = WeatherForecast(
            location = Location(name = "", country = ""),
            current = CurrentWeather(
                temp_c = 0.0,
                feelslike_c = 0.0,
                wind_mph = 0.0,
                humidity = 0,
                condition = Condition(
                    text = "",
                    icon = ""
                )
            ),
            forecast = Forecast(
                forecastday = listOf(
                    ForecastDay(
                        date = "",
                        day = Day(
                            avgtemp_c = 0.0,
                            condition = Condition(
                                text = "",
                                icon = ""
                            )
                        ),
                        hour = listOf(
                            Hour(
                                time = "",
                                temp_c = 0.0,
                                condition = Condition(
                                    text = "",
                                    icon = ""
                                )
                            )
                        )
                    )
                )
            )
        )


        val SAMPLE = WeatherForecast(
            location = Location(
                name = "Madrid",
                country = "Spain"
            ),
            current = CurrentWeather(
                temp_c = 24.2,
                feelslike_c = 25.4,
                wind_mph = 5.6,
                humidity = 57,
                condition = Condition(
                    text = "Clear",
                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                )
            ),
            forecast = Forecast(
                forecastday = listOf(
                    ForecastDay(
                        date = "2024-09-03",
                        day = Day(
                            avgtemp_c = 25.6,
                            condition = Condition(
                                text = "Sunny",
                                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                            )
                        ),
                        hour = listOf(
                            Hour(
                                time = "2024-09-03 00:00",
                                temp_c = 24.5,
                                condition = Condition(
                                    text = "Clear",
                                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                                )
                            ),
                            Hour(
                                time = "2024-09-03 01:00",
                                temp_c = 23.7,
                                condition = Condition(
                                    text = "Clear",
                                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                                )
                            ),
                            Hour(
                                time = "2024-09-03 02:00",
                                temp_c = 22.9,
                                condition = Condition(
                                    text = "Clear",
                                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                                )
                            )
                        )
                    ),
                    ForecastDay(
                        date = "2024-09-04",
                        day = Day(
                            avgtemp_c = 85.6,
                            condition = Condition(
                                text = "Sunny",
                                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                            )
                        ),
                        hour = listOf(
                            Hour(
                                time = "2024-09-03 00:00",
                                temp_c = 24.5,
                                condition = Condition(
                                    text = "Clear",
                                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                                )
                            ),
                            Hour(
                                time = "2024-09-03 01:00",
                                temp_c = 23.7,
                                condition = Condition(
                                    text = "Clear",
                                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                                )
                            ),
                            Hour(
                                time = "2024-09-03 02:00",
                                temp_c = 22.9,
                                condition = Condition(
                                    text = "Clear",
                                    icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"
                                )
                            )
                        )
                    ),


                )
            )
        )
    }
}



