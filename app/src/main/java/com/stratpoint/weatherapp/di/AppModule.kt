package com.stratpoint.weatherapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.stratpoint.weatherapp.BuildConfig
import com.stratpoint.weatherapp.data.database.AppDatabase
import com.stratpoint.weatherapp.data.database.DbConstants
import com.stratpoint.weatherapp.data.network.RetrofitBuilder
import com.stratpoint.weatherapp.presentation.home.current_weather.DefaultLocationTracker
import com.stratpoint.weatherapp.presentation.home.current_weather.LocationTracker
import com.stratpoint.weatherapp.presentation.home.data.service.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(
        app: Application
    ): Context = app.applicationContext

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideAppDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DbConstants.DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideRetrofit(
        context: Context
    ) = RetrofitBuilder.createRetrofitInstance(
        context,
        "https://api.openweathermap.org/data/2.5/",
        BuildConfig.DEBUG
    )

    @Singleton
    @Provides
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = DefaultLocationTracker(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )

}