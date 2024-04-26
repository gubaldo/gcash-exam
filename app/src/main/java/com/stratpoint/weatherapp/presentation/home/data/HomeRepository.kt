package com.stratpoint.weatherapp.presentation.home.data

import com.google.firebase.auth.FirebaseAuth
import com.stratpoint.weatherapp.data.database.AppDatabase
import com.stratpoint.weatherapp.data.network.BaseRepository
import com.stratpoint.weatherapp.presentation.home.data.service.WeatherApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val weatherApiService: WeatherApiService,
    private val appDatabase: AppDatabase
) : BaseRepository() {

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    suspend fun getWeather(lat: Double, lon: Double, apiKey: String) = serviceCall {
        weatherApiService.getWeather(lat, lon, apiKey)
    }

    suspend fun logout() = serviceCall {
        firebaseAuth.signOut()
        appDatabase.apply {
            userDao().deleteUser()
        }
    }

}