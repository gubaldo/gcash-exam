package com.stratpoint.weatherapp.presentation.home.current_weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.stratpoint.weatherapp.BuildConfig
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.presentation.home.constant.IconUtil.getIcon
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.theme.spacing
import com.stratpoint.weatherapp.ui.views.dialog.ProgressDialog
import com.stratpoint.weatherapp.ui.views.icon.WeatherIcon
import com.stratpoint.weatherapp.util.DateUtil.TIME_FORMAT
import com.stratpoint.weatherapp.util.DateUtil.unixTimeToDateString
import com.stratpoint.weatherapp.util.observeAsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CurrentWeatherScreen(
    modifier: Modifier,
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

    LaunchedEffect(locationPermissions.allPermissionsGranted, screenState.user) {

        if (locationPermissions.allPermissionsGranted) {
            screenState.hasPermissions.value = true
            viewModel.getLocation()
        }

    }

    LaunchedEffect(screenState.currentLocation.value, screenState.user.value) {
        val currentLocation = screenState.currentLocation.value
        val user = screenState.user.value

        if (currentLocation != null && user != null) {
            viewModel.getWeather(
                currentLocation.latitude,
                currentLocation.longitude,
                BuildConfig.API_KEY,
                user.id
            )
        }
    }

    InitializeScreenState(screenState, viewModel)

    CurrentWeatherContent(
        modifier,
        screenState,
    )

}

@Composable
fun CurrentWeatherContent(
    modifier: Modifier,
    screenState: CurrentWeatherScreenState
) {

    if (screenState.hasPermissions.value && !screenState.isLoading.value && screenState.weather.value.id.toInt() != 0) {

        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large)
        ) {
            val (iconWeatherRef, degreeCelsiusTextRef, locationTextRef, sunriseTextRef, sunsetTextRef) = createRefs()

            WeatherIconView(
                modifier = Modifier
                    .constrainAs(iconWeatherRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = MaterialTheme.spacing.large)
                    .size(100.dp),
                screenState = screenState
            )

            DegreeCelsiusText(
                modifier = Modifier
                    .constrainAs(degreeCelsiusTextRef) {
                        top.linkTo(iconWeatherRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(vertical = MaterialTheme.spacing.small),
                screenState = screenState
            )

            LocationText(
                modifier = Modifier
                    .constrainAs(locationTextRef) {
                        top.linkTo(degreeCelsiusTextRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                screenState = screenState
            )

            SunriseText(
                modifier = Modifier
                    .constrainAs(sunriseTextRef) {
                        top.linkTo(locationTextRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(
                        top = MaterialTheme.spacing.large,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    ),
                screenState = screenState
            )

            SunsetText(
                modifier = Modifier
                    .constrainAs(sunsetTextRef) {
                        top.linkTo(sunriseTextRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    ),
                screenState = screenState
            )

        }

    }

    if (screenState.isLoading.value) {
        LoadingDialog(screenState)
    }

}

@Composable
fun WeatherIconView(
    modifier: Modifier,
    screenState: CurrentWeatherScreenState
) {

    val icon = getIcon(screenState.weather.value.weatherId, screenState.weather.value.date)

    WeatherIcon(modifier = modifier, imageId = icon)
}

@Composable
fun DegreeCelsiusText(
    modifier: Modifier = Modifier,
    screenState: CurrentWeatherScreenState
) {
    Text(
        modifier = modifier,
        text = stringResource(
            id = R.string.label_degree_celsius,
            screenState.weather.value.temperature
        ),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LocationText(
    modifier: Modifier = Modifier,
    screenState: CurrentWeatherScreenState
) {
    Text(
        modifier = modifier,
        text = "${screenState.weather.value.city}, ${screenState.weather.value.country}",
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SunriseText(
    modifier: Modifier = Modifier,
    screenState: CurrentWeatherScreenState
) {

    val formattedSunrise = unixTimeToDateString(screenState.weather.value.sunrise, TIME_FORMAT)

    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_sunrise, formattedSunrise),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SunsetText(
    modifier: Modifier = Modifier,
    screenState: CurrentWeatherScreenState
) {

    val formattedSunset = unixTimeToDateString(screenState.weather.value.sunset, TIME_FORMAT)

    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_sunset, formattedSunset),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start
    )
}

@Composable
fun LoadingDialog(screenState: CurrentWeatherScreenState) {
    val showDialog = screenState.isLoading.value

    if (showDialog) {
        ProgressDialog(
            showDialog = true,
            message = stringResource(id = R.string.progress_loading)
        )
    }
}

@Composable
private fun InitializeScreenState(
    screenState: CurrentWeatherScreenState,
    viewModel: CurrentWeatherViewModel
) {
    screenState.currentLocation.value = viewModel.currentLocation.collectAsState().value
    screenState.user.value = viewModel.user.collectAsState().value
    screenState.isLoading.value = viewModel.isLoading.collectAsState().value
    screenState.weather.value = viewModel.weather.collectAsState().value
}

@Preview(showBackground = true)
@Composable
private fun CurrentWeatherPreview() {
    WeatherAppTheme {
        CurrentWeatherContent(
            Modifier,
            screenState = rememberCurrentWeatherScreenState()
        )
    }
}