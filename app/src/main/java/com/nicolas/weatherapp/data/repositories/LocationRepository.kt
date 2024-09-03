package com.nicolas.weatherapp.data.repositories

import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast

interface LocationRepository {

    suspend fun getLocations(query: String): List<Location>

    suspend fun getDetailsLocation(cityName: String): WeatherForecast

    fun saveSearch(location: Location)

    fun loadRecentSearches(): List<Location>

}