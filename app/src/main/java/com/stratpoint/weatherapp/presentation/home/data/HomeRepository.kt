package com.stratpoint.weatherapp.presentation.home.data

import com.google.firebase.auth.FirebaseAuth
import com.stratpoint.weatherapp.data.database.AppDatabase
import com.stratpoint.weatherapp.data.network.BaseRepository
import com.stratpoint.weatherapp.domain.Weather
import com.stratpoint.weatherapp.presentation.home.data.service.WeatherApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val weatherApiService: WeatherApiService,
    private val appDatabase: AppDatabase
) : BaseRepository() {

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    fun getUserFlow() = appDatabase.userDao().getLoggedInUser()

    suspend fun getWeather(lat: Double, lon: Double, apiKey: String, userId: String) = serviceCall {
        val weatherResponse = weatherApiService.getWeather(lat, lon, apiKey)
        val weatherStatus = weatherResponse.weatherStatus[0]
        val weather = appDatabase.weatherDao().insert(
            Weather(
                userId = userId,
                weatherId = weatherStatus.id,
                weatherStatus = weatherStatus.main,
                temperature = weatherResponse.weatherTemperature.temp.toString(),
                date = weatherResponse.date,
                timezone = weatherResponse.timezone,
                city = weatherResponse.name,
                country = weatherResponse.weatherSystem.country,
                sunrise = weatherResponse.weatherSystem.sunrise,
                sunset = weatherResponse.weatherSystem.sunset
            )
        )

        appDatabase.weatherDao().getWeatherById(weather)

    }

    suspend fun logout() = serviceCall {
        firebaseAuth.signOut()
        appDatabase.apply {
            userDao().deleteUser()
        }
    }

    suspend fun getWeatherHistoryFlow() = appDatabase.weatherDao()
        .getWeatherByUserIdListFlow(appDatabase.userDao().getCurrentLoggedInUserId())

}