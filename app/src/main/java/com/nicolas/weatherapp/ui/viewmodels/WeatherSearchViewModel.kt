package com.nicolas.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.usecases.GetLocationsUseCase
import com.nicolas.weatherapp.domain.usecases.SearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val manageSearchHistoryUseCase: SearchHistoryUseCase
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Location>>()
    val searchResults: LiveData<List<Location>> get() = _searchResults

    private val _recentSearches = MutableLiveData<List<Location>>()
    val recentSearches: LiveData<List<Location>> get() = _recentSearches

    private val _selectedCityName = MutableLiveData<String>()
    val selectedCityName: LiveData<String> get() = _selectedCityName

    init {
        loadRecentSearches()
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _recentSearches.value = manageSearchHistoryUseCase.getRecentSearches()
                _searchResults.value = emptyList()
            } else {
                _searchResults.value = getLocationsUseCase(query)
            }
        }
    }

    fun onSearchItemClicked(location: Location) {
        _selectedCityName.value = location.name
        manageSearchHistoryUseCase.saveSearch(location)
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        _recentSearches.value = manageSearchHistoryUseCase.getRecentSearches()
    }
}

