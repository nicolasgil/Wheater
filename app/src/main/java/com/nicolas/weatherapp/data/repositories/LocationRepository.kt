package com.nicolas.weatherapp.data.repositories

import com.nicolas.weatherapp.domain.models.Location

interface LocationRepository {

    suspend fun getLocations(query: String): List<Location>

    fun saveSearch(location: Location)

    fun loadRecentSearches(): List<Location>

}