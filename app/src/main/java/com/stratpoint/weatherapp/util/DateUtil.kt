package com.stratpoint.weatherapp.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    const val DATE_FORMAT = "MMM dd, yyyy hh:mm a"

    fun getDate(newFormat: String, date: Date): String {
        val dateFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        return dateFormat.format(date) ?: ""
    }

    fun unixTimeToDateString(unixTime: Long, format: String = DATE_FORMAT): String {
        val millisecondsUpdated = unixTime * 1000
        val calendar = Calendar.getInstance()
            .apply {
                timeInMillis = millisecondsUpdated
            }
        return getDate(format, calendar.time)
    }

}