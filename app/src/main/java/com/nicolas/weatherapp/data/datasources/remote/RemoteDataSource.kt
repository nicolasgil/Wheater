package com.nicolas.weatherapp.data.datasources.remote

import com.nicolas.weatherapp.domain.models.Location

interface RemoteDataSource {

    suspend fun getAllLocations(query: String): List<Location>

}