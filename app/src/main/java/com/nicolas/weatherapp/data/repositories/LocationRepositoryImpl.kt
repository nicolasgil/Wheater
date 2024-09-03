package com.nicolas.weatherapp.data.repositories

import android.util.Log
import com.nicolas.weatherapp.data.datasources.local.SearchHistoryDataSource
import com.nicolas.weatherapp.data.datasources.remote.RemoteDataSource
import com.nicolas.weatherapp.domain.models.Forecast
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val searchHistoryDataSource: SearchHistoryDataSource
) : LocationRepository {

    override suspend fun getLocations(query: String): List<Location> {
        return try {
            remoteDataSource.getAllLocations(query)
        } catch (e: Exception) {
            Log.e("LocationRepositoryImpl", "Error fetching Locations", e)
            emptyList()
        }
    }


    override suspend fun getDetailsLocation(cityName: String): WeatherForecast {

        return try {
            val response = remoteDataSource.getDetailsLocation(cityName)
            response
        } catch (e: Exception) {
            Log.e("LocationRemoteData", "Error fetching remote locations", e)
            WeatherForecast(
                location = Location(name = "", country = ""),
                forecast = Forecast(
                    forecastday = emptyList()
                )
            )
        }
    }

    override fun saveSearch(location: Location) {
        try {
            searchHistoryDataSource.saveSearch(location)
        } catch (e: Exception) {
            Log.e("LocationRepositoryImpl", "Error saving Locations", e)
        }

    }

    override fun loadRecentSearches(): List<Location> {
        return try {
            searchHistoryDataSource.loadRecentSearches()
        } catch (e: Exception) {
            Log.e("LocationRepositoryImpl", "Error loading recent Locations", e)
            emptyList()
        }
    }

}