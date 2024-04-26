package com.stratpoint.weatherapp.presentation.home.current_weather

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.data.network.Status
import com.stratpoint.weatherapp.domain.User
import com.stratpoint.weatherapp.domain.Weather
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

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _weather = MutableStateFlow(Weather())
    val weather = _weather.asStateFlow()

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUserFlow().collect {
                it?.let {
                    _user.value = it
                }
            }
        }
    }

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
        apiKey: String,
        userId: String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getWeather(lat, long, apiKey, userId)
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let {
                        _weather.value = it
                    }
                }

                Status.ERROR -> {
                    _errorMessage.value = result.message
                }
            }

            _isLoading.value = false
        }

    }

    fun dismissErrorDialog() {
        _errorMessage.value = null
    }

}