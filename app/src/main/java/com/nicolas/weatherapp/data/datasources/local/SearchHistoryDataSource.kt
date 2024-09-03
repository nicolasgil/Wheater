package com.nicolas.weatherapp.data.datasources.local

import com.nicolas.weatherapp.domain.models.Location

interface SearchHistoryDataSource {

    fun saveSearch(location: Location)

    fun loadRecentSearches(): List<Location>

}