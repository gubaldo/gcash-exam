package com.stratpoint.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stratpoint.weatherapp.data.database.dao.UserDao
import com.stratpoint.weatherapp.data.database.dao.WeatherDao
import com.stratpoint.weatherapp.domain.User
import com.stratpoint.weatherapp.domain.Weather

@Database(
    entities = [
        User::class,
        Weather::class
    ],
    version = DbConstants.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun weatherDao(): WeatherDao

}