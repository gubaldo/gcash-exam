package com.stratpoint.weatherapp.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    const val DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a"
    const val TIME_FORMAT = "hh:mm a"

    private fun getDate(newFormat: String, date: Date): String {
        val dateFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun unixTimeToDateString(unixTime: Long, format: String = DATE_TIME_FORMAT): String {
        val millisecondsUpdated = unixTime * 1000
        val calendar = Calendar.getInstance()
            .apply {
                timeInMillis = millisecondsUpdated
            }
        return getDate(format, calendar.time)
    }

    fun isPastSix(unixTime: Long): Boolean {
        val millisecondsUpdated = unixTime * 1000
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millisecondsUpdated
        }
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        return hourOfDay >= 18
    }

}