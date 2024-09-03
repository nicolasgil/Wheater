package com.nicolas.weatherapp.data.datasources.remote

import com.nicolas.weatherapp.data.datasources.remote.remotemodel.ForecastResponse
import com.nicolas.weatherapp.domain.models.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): List<Location>


    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int
    ): ForecastResponse


}