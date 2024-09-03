package com.nicolas.weatherapp.domain.usecases

import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.domain.models.Location
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    fun saveSearch(location: Location) {
        try {
            locationRepository.saveSearch(location)
        } catch (e: Exception) {
            Log.e("GetLocationsUseCase", "Error fetching locations", e)

        }
    }

    fun getRecentSearches(): List<Location> {
        return locationRepository.loadRecentSearches()
    }

}