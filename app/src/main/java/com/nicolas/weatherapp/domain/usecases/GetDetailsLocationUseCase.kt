package com.nicolas.weatherapp.domain.usecases

import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.domain.models.WeatherForecast
import com.nicolas.weatherapp.utils.WeatherForecastObject
import javax.inject.Inject

class GetDetailsLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(cityName: String): WeatherForecast {
        return try {
            locationRepository.getDetailsLocation(cityName)
        } catch (e: Exception) {
            Log.e("GetLocationsUseCase", "Error fetching locations", e)
            WeatherForecastObject.EMPTY
        }
    }
}