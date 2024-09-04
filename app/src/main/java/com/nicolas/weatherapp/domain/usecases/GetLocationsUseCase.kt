package com.nicolas.weatherapp.domain.usecases

import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.domain.models.Location
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(query: String): List<Location> {
        return try {
            locationRepository.getLocations(query)
        } catch (e: Exception) {
            Log.e("GetLocationsUseCase", "Error fetching locations", e)
            emptyList()
        }
    }
}