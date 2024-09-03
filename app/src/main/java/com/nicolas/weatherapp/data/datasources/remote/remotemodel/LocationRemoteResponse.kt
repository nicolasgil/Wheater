package com.nicolas.weatherapp.data.datasources.remote.remotemodel

import com.google.gson.annotations.SerializedName

data class LocationRemoteResponse(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String
)


