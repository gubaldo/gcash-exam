package com.stratpoint.weatherapp.presentation.home.current_weather

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}