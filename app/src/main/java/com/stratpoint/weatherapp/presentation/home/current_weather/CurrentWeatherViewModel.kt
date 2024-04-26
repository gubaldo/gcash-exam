package com.stratpoint.weatherapp.presentation.home.current_weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.presentation.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val repository: HomeRepository
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun getLocation() {
        viewModelScope.launch {
            _currentLocation.value = locationTracker.getCurrentLocation()
        }
    }

    fun resetLocation() {
        _currentLocation.value = null
    }

    fun getWeather(
        lat: Double,
        long: Double,
        apiKey: String
    ) {
        viewModelScope.launch {
            val test = repository.getWeather(lat, long, apiKey)
            Log.e("TEST", test.toString())
        }

    }

}