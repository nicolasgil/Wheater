package com.nicolas.weatherapp.domain.usecases

import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.domain.models.Condition
import com.nicolas.weatherapp.domain.models.CurrentWeather
import com.nicolas.weatherapp.domain.models.Forecast
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast
import javax.inject.Inject

class GetDetailsLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(cityName: String): WeatherForecast {
        return try {
            locationRepository.getDetailsLocation(cityName)
        } catch (e: Exception) {
            Log.e("GetLocationsUseCase", "Error fetching locations", e)
            WeatherForecast(
                location = Location(name = "", country = ""),
                current = CurrentWeather(0.0, Condition("", "")),
                forecast = Forecast(
                    forecastday = emptyList()
                )
            )
        }
    }
}