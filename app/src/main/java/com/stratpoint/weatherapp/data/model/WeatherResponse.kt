package com.stratpoint.weatherapp.data.model

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("weather") val weatherStatus: List<WeatherStatus>,
    @SerializedName("main") val weatherTemperature: WeatherTemperature,
    @SerializedName("dt") val date: Long,
    @SerializedName("sys") val weatherSystem: WeatherSystem,
    val name: String,
    val timezone: Long
)