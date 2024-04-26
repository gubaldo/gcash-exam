package com.stratpoint.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stratpoint.weatherapp.data.database.BaseDao
import com.stratpoint.weatherapp.domain.Weather
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao : BaseDao<Weather> {

    @Query("SELECT * FROM weather WHERE userId = :userId ORDER BY date DESC")
    abstract fun getWeatherByUserIdListFlow(userId: String): Flow<List<Weather>>

    @Query("SELECT * FROM weather WHERE id = :id")
    abstract suspend fun getWeatherById(id: Long): Weather

}