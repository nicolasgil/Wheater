package com.nicolas.weatherapp.data.datasources.remote

import android.util.Log
import com.nicolas.weatherapp.data.datasources.remote.remotemodel.LocationRemoteResponse
import com.nicolas.weatherapp.domain.models.Location
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(
    private val locationService: LocationService,
) : RemoteDataSource {

    private val apiKey = "de5553176da64306b86153651221606"

    override suspend fun getAllLocations(query: String): List<Location> {

        return try {
            val response = locationService.searchLocations(apiKey = apiKey, query = query)
            response ?: emptyList()
        } catch (e: Exception) {
            Log.e("LocationRemoteData", "Error fetching remote locations", e)
            emptyList()
        }
    }


    private fun List<LocationRemoteResponse>.toDomainModel(): List<Location> =
        map { it.toDomainModel() }

    private fun LocationRemoteResponse.toDomainModel(): Location =
        Location(
            name, country
        )
}
