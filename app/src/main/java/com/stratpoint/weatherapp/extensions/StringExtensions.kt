package com.stratpoint.weatherapp.extensions

fun String.isValidEmail(): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return this.matches(emailRegex)
}