package com.stratpoint.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stratpoint.weatherapp.data.database.BaseDao
import com.stratpoint.weatherapp.domain.Weather
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao : BaseDao<Weather> {

    @Query("SELECT * FROM weather WHERE userId = :userId")
    abstract fun getWeatherByUserIdListFlow(userId: Long): Flow<List<Weather>>

}