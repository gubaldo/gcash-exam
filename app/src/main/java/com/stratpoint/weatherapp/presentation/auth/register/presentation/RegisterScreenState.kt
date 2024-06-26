package com.stratpoint.weatherapp.presentation.auth.register.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class RegisterScreenState(
    val name: MutableState<String>,
    val email: MutableState<String>,
    val password: MutableState<String>,
    val isValidName: MutableState<Boolean>,
    val isValidEmail: MutableState<Boolean>,
    val isValidPassword: MutableState<Boolean>,
    val isRegisterButtonEnabled: MutableState<Boolean>,
    val isLoading: MutableState<Boolean>,
    val errorMessage: MutableState<String?>
)

@Composable
fun rememberRegisterScreenState(
    name: MutableState<String> = mutableStateOf(""),
    email: MutableState<String> = mutableStateOf(""),
    password: MutableState<String> = mutableStateOf(""),
    isValidName: MutableState<Boolean> = mutableStateOf(false),
    isValidEmail: MutableState<Boolean> = mutableStateOf(false),
    isValidPassword: MutableState<Boolean> = mutableStateOf(false),
    isRegisterButtonEnabled: MutableState<Boolean> = mutableStateOf(false),
    isLoading: MutableState<Boolean> = mutableStateOf(false),
    errorMessage: MutableState<String?> = mutableStateOf(null)
) = remember {
    RegisterScreenState(
        name = name,
        email = email,
        password = password,
        isValidName = isValidName,
        isValidEmail = isValidEmail,
        isValidPassword = isValidPassword,
        isRegisterButtonEnabled = isRegisterButtonEnabled,
        isLoading = isLoading,
        errorMessage = errorMessage
    )
}
