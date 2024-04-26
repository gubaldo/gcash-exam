package com.stratpoint.weatherapp.presentation.home.constant

import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.util.DateUtil

object IconUtil {
    fun getIcon(weatherId: Int, date: Long): Int {
        return when (weatherId) {
            in 200..531 -> WeatherIconDisplay.IC_RAIN.icon
            in 800..804 -> {
                if (DateUtil.isPastSix(date)) {
                    WeatherIconDisplay.IC_MOON.icon
                } else {
                    WeatherIconDisplay.IC_SUN.icon
                }

            }

            else -> R.drawable.ic_cloud
        }
    }
}