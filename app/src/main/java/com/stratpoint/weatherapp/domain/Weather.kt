package com.stratpoint.weatherapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String = "",
    val weatherId: Int = 0,
    val weatherStatus: String = "",
    val temperature: String = "",
    val date: Long = 0,
    val timezone: Long = 0,
    val city: String = "",
    val country: String = "",
    val sunrise: Long = 0,
    val sunset: Long = 0
)