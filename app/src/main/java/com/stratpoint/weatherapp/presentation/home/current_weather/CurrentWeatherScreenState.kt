package com.stratpoint.weatherapp.presentation.home.current_weather

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class CurrentWeatherScreenState(
    val currentLocation: MutableState<Location?>
)

@Composable
fun rememberCurrentWeatherScreenState(
    currentLocation: MutableState<Location?> = mutableStateOf(null)
) = remember {
    CurrentWeatherScreenState(
        currentLocation = currentLocation
    )
}