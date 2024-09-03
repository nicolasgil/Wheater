package com.nicolas.weatherapp.data.datasources.remote

import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast

interface RemoteDataSource {

    suspend fun getAllLocations(query: String): List<Location>

    suspend fun getDetailsLocation(cityName: String): WeatherForecast

}