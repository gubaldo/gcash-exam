package com.stratpoint.weatherapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val id: String,
    val userId: String,
    val weatherId: Int,
    val weatherStatus: String,
    val temperature: String,
    val date: Long,
    val timezone: Long,
    val city: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)