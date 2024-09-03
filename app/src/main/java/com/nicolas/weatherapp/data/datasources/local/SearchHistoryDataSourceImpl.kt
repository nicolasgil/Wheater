package com.nicolas.weatherapp.data.datasources.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nicolas.weatherapp.data.datasources.local.localmodel.SearchHistoryModel
import com.nicolas.weatherapp.domain.models.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchHistoryDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SearchHistoryDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("recent_searches", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun saveSearch(location: Location) {
        val recentSearches = loadRecentSearches().toMutableList()

        recentSearches.remove(location)

        recentSearches.add(location)

        if (recentSearches.size > 3) {
            recentSearches.removeAt(0)
        }

        val json = gson.toJson(recentSearches)
        sharedPreferences.edit().putString("recent_searches", json).apply()
    }



    override fun loadRecentSearches(): List<Location> {
        val json = sharedPreferences.getString("recent_searches", null) ?: return emptyList()
        val type = object : TypeToken<List<SearchHistoryModel>>() {}.type
        val recentSearchModels: List<SearchHistoryModel> = gson.fromJson(json, type)

        return recentSearchModels.toDomainModel()
    }


    private fun List<SearchHistoryModel>.toDomainModel(): List<Location> =
        map { it.toDomainModel() }


    private fun SearchHistoryModel.toDomainModel(): Location =
        Location(
            name = this.name,
            country = this.country
        )


    private fun List<Location>.toPersistenceModel(): List<SearchHistoryModel> =
        map { it.toPersistenceModel() }

    private fun Location.toPersistenceModel(): SearchHistoryModel =
        SearchHistoryModel(
            name = this.name,
            country = this.country
        )
}
