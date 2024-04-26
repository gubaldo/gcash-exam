package com.stratpoint.weatherapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class HomeScreenState(
    val showConfirmLogout: MutableState<Boolean>,
    val logoutSuccess: MutableState<Boolean>
)

@Composable
fun rememberHomeScreenState(
    showConfirmLogout: MutableState<Boolean> = mutableStateOf(false),
    logoutSuccess: MutableState<Boolean> = mutableStateOf(false)
) = remember {
    HomeScreenState(
        showConfirmLogout = showConfirmLogout,
        logoutSuccess = logoutSuccess
    )
}