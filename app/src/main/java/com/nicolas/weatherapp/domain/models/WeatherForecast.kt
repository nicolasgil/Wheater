package com.nicolas.weatherapp.domain.models

data class WeatherForecast(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val country: String
)

data class CurrentWeather(
    val temp_c: Double,
    val condition: Condition,
    val feelslike_c: Double,
    val wind_mph: Double,
    val humidity: Int
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)

data class Day(
    val avgtemp_c: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)

data class Hour(
    val time: String,
    val temp_c: Double,
    val condition: Condition
)

