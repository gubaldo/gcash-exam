package com.stratpoint.weatherapp.auth.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class LoginScreenState(
    val email: MutableState<String>,
    val isValidEmail: MutableState<Boolean>,
    val password: MutableState<String>,
    val isValidPassword: MutableState<Boolean>,
    val isLoginButtonEnabled: MutableState<Boolean>
)

@Composable
fun rememberLoginScreenState(
    email: MutableState<String> = mutableStateOf(""),
    isValidEmail: MutableState<Boolean> = mutableStateOf(false),
    password: MutableState<String> = mutableStateOf(""),
    isValidPassword: MutableState<Boolean> = mutableStateOf(false),
    isLoginButtonEnabled: MutableState<Boolean> = mutableStateOf(false)
) = remember {
    LoginScreenState(
        email = email,
        isValidEmail = isValidEmail,
        password = password,
        isValidPassword = isValidPassword,
        isLoginButtonEnabled = isLoginButtonEnabled
    )
}