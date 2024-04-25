package com.stratpoint.weatherapp.presentation.home.current_weather

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.util.observeAsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CurrentWeatherScreen(
    modifier: Modifier,
    navigateToAuth: () -> Unit,
    screenState: CurrentWeatherScreenState = rememberCurrentWeatherScreenState(),
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val lifecycleState = LocalLifecycleOwner.current.lifecycle.observeAsState().value

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.Event.ON_START -> {
                locationPermissions.launchMultiplePermissionRequest()
            }

            Lifecycle.Event.ON_STOP -> {
                viewModel.resetLocation()
            }

            else -> {}
        }
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        viewModel.getLocation()
    }

    LaunchedEffect(screenState.currentLocation.value) {
        screenState.currentLocation.value?.let {
            Log.e("TEST", it.toString())
        }
    }

    InitializeScreenState(screenState, viewModel)

    CurrentWeatherContent(
        modifier,
        navigateToAuth
    )

}

@Composable
fun CurrentWeatherContent(
    modifier: Modifier,
    navigateToAuth: () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(text = AnnotatedString("CurrentWeather Screen")) {
            navigateToAuth()
        }
    }
}

@Composable
private fun InitializeScreenState(
    screenState: CurrentWeatherScreenState,
    viewModel: CurrentWeatherViewModel
) {
    screenState.currentLocation.value = viewModel.currentLocation.collectAsState().value
}

@Preview
@Composable
private fun CurrentWeatherPreview() {
    WeatherAppTheme {
        CurrentWeatherContent(
            Modifier,
            {}
        )
    }
}