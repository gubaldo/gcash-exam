package com.stratpoint.weatherapp.presentation.home.current_weather

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.stratpoint.weatherapp.domain.User
import com.stratpoint.weatherapp.domain.Weather

class CurrentWeatherScreenState(
    val hasPermissions: MutableState<Boolean>,
    val currentLocation: MutableState<Location?>,
    val user: MutableState<User?>,
    val isLoading: MutableState<Boolean>,
    val weather: MutableState<Weather?>,
)

@Composable
fun rememberCurrentWeatherScreenState(
    hasPermissions: MutableState<Boolean> = mutableStateOf(false),
    currentLocation: MutableState<Location?> = mutableStateOf(null),
    user: MutableState<User?> = mutableStateOf(null),
    isLoading: MutableState<Boolean> = mutableStateOf(false),
    weather: MutableState<Weather?> = mutableStateOf(null)
) = remember {
    CurrentWeatherScreenState(
        hasPermissions = hasPermissions,
        currentLocation = currentLocation,
        user = user,
        isLoading = isLoading,
        weather = weather
    )
}