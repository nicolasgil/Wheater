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
    val condition: Condition
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day
)

data class Day(
    val avgtemp_c: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)
