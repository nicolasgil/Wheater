package com.nicolas.weatherapp.data.datasources.local.localmodel

import com.google.gson.annotations.SerializedName

data class SearchHistoryModel(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String
)
