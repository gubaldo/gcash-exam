package com.stratpoint.weatherapp.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}