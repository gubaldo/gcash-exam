package com.stratpoint.weatherapp.presentation.home.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.stratpoint.weatherapp.domain.Weather

class HistoryScreenState(
    val weatherList: MutableState<List<Weather>>
)

@Composable
fun rememberHistoryScreenState(
    weatherList: MutableState<List<Weather>> = mutableStateOf(mutableStateListOf())
) = remember {
    HistoryScreenState(
        weatherList = weatherList
    )
}