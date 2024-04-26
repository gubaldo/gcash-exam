package com.stratpoint.weatherapp.presentation.home.current_weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.theme.spacing
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

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        viewModel.getLocation()
    }

    LaunchedEffect(screenState.currentLocation.value) {
        screenState.currentLocation.value?.let {
            viewModel.getWeather(it.latitude, it.longitude, "cf4992b73b315a2d160e74538dec2495")
        }
    }

    InitializeScreenState(screenState, viewModel)

    CurrentWeatherContent(
        modifier
    )

}

@Composable
fun CurrentWeatherContent(
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.large)
    ) {
        val (iconWeatherRef, degreeCelsiusTextRef, locationTextRef, sunriseTextRef, sunsetTextRef) = createRefs()

        WeatherIcon(
            modifier = Modifier
                .constrainAs(iconWeatherRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = MaterialTheme.spacing.large)
                .size(100.dp)
        )

        DegreeCelsiusText(
            modifier = Modifier
                .constrainAs(degreeCelsiusTextRef) {
                    top.linkTo(iconWeatherRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(vertical = MaterialTheme.spacing.small)
        )

        LocationText(
            modifier = Modifier
                .constrainAs(locationTextRef) {
                    top.linkTo(degreeCelsiusTextRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
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
                )
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
                )
        )

    }
}

@Composable
fun WeatherIcon(
    modifier: Modifier
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_sun),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun DegreeCelsiusText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_degree_celsius, "32"),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LocationText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Test Location",
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SunriseText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_sunrise),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SunsetText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_sunset),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun InitializeScreenState(
    screenState: CurrentWeatherScreenState,
    viewModel: CurrentWeatherViewModel
) {
    screenState.currentLocation.value = viewModel.currentLocation.collectAsState().value
}

@Preview(showBackground = true)
@Composable
private fun CurrentWeatherPreview() {
    WeatherAppTheme {
        CurrentWeatherContent(
            Modifier
        )
    }
}