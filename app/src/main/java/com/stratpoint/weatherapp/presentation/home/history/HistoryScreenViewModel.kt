package com.stratpoint.weatherapp.presentation.home.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.domain.Weather
import com.stratpoint.weatherapp.presentation.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _weatherList = MutableStateFlow(emptyList<Weather>())
    val weatherList = _weatherList.asStateFlow()

    init {
        viewModelScope.launch {
            // Observe weather history from db for stream of data
            repository.getWeatherHistoryFlow().collect {
                _weatherList.value = it
            }
        }
    }
}