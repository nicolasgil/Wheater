package com.nicolas.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.models.WeatherForecast
import com.nicolas.weatherapp.domain.usecases.GetDetailsLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getDetailsLocationsUseCase: GetDetailsLocationUseCase
) : ViewModel() {

    private val _detailsLocation = MutableLiveData<WeatherForecast>()
    val detailsLocation: LiveData<WeatherForecast> get() = _detailsLocation


    fun loadForecast(cityName: String) {
        viewModelScope.launch {
            _detailsLocation.value = getDetailsLocationsUseCase(cityName)
        }
    }
}
