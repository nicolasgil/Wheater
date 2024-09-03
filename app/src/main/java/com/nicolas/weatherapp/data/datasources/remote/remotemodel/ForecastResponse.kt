package com.nicolas.weatherapp.data.datasources.remote.remotemodel

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current,
    @SerializedName("forecast") val forecast: Forecast
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Float,
    @SerializedName("condition") val condition: Condition
)

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: Day
)

data class Day(
    @SerializedName("avgtemp_c") val avgtempC: Float,
    @SerializedName("condition") val condition: Condition
)




