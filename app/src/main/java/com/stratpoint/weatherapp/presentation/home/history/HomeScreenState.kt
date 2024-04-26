package com.stratpoint.weatherapp.presentation.home.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class HomeScreenState(
    val showDropdownMenu: MutableState<Boolean>,
    val showConfirmLogout: MutableState<Boolean>,
    val logoutSuccess: MutableState<Boolean>
)

@Composable
fun rememberHomeScreenState(
    showDropdownMenu: MutableState<Boolean> = mutableStateOf(false),
    showConfirmLogout: MutableState<Boolean> = mutableStateOf(false),
    logoutSuccess: MutableState<Boolean> = mutableStateOf(false)
) = remember {
    HomeScreenState(
        showDropdownMenu = showDropdownMenu,
        showConfirmLogout = showConfirmLogout,
        logoutSuccess = logoutSuccess
    )
}