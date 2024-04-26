package com.stratpoint.weatherapp.presentation.home.history

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.domain.Weather
import com.stratpoint.weatherapp.presentation.home.constant.IconUtil.getIcon
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.theme.spacing
import com.stratpoint.weatherapp.ui.views.icon.WeatherIcon
import com.stratpoint.weatherapp.util.DateUtil

@Composable
fun HistoryScreen(
    modifier: Modifier,
    screenState: HistoryScreenState = rememberHistoryScreenState(),
    viewModel: HistoryScreenViewModel = hiltViewModel()
) {

    InitializeScreenState(screenState, viewModel)

    HistoryContent(modifier = modifier, screenState)

}

@Composable
fun InitializeScreenState(
    screenState: HistoryScreenState,
    viewModel: HistoryScreenViewModel
) {
    screenState.weatherList.value = viewModel.weatherList.collectAsState().value
}

@Composable
fun HistoryContent(
    modifier: Modifier,
    screenState: HistoryScreenState
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.medium),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(screenState.weatherList.value) {
            WeatherListCard(item = it)
        }
    }
}

@Composable
fun WeatherListCard(
    modifier: Modifier = Modifier,
    item: Weather
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {

            val (iconRef, temperatureRef, locationRef, dateRef, sunriseSunsetRef) = createRefs()

            val icon = getIcon(item.weatherId, item.date)

            WeatherIcon(
                imageId = icon,
                modifier = Modifier
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(50.dp)
            )

            TemperatureText(
                temperature = item.temperature,
                modifier = Modifier
                    .constrainAs(temperatureRef) {
                        top.linkTo(iconRef.bottom)
                        linkTo(start = iconRef.start, end = iconRef.end)
                    }
                    .padding(top = 8.dp)
            )

            LocationText(
                city = item.city,
                country = item.country,
                modifier = Modifier
                    .constrainAs(locationRef) {
                        top.linkTo(parent.top)
                        start.linkTo(iconRef.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 20.dp)
            )

            DateText(
                date = DateUtil.unixTimeToDateString(item.date),
                modifier = Modifier
                    .constrainAs(dateRef) {
                        top.linkTo(locationRef.bottom)
                        start.linkTo(iconRef.end)
                    }
                    .padding(start = 20.dp, top = 4.dp)
            )

            SunriseSunsetText(
                sunriseTime = DateUtil.unixTimeToDateString(item.sunrise, DateUtil.TIME_FORMAT),
                sunsetTime = DateUtil.unixTimeToDateString(item.sunset, DateUtil.TIME_FORMAT),
                modifier = Modifier
                    .constrainAs(sunriseSunsetRef) {
                        top.linkTo(dateRef.bottom)
                        start.linkTo(iconRef.end)
                    }
                    .padding(start = 20.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun TemperatureText(
    temperature: String,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_degree_celsius, temperature),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LocationText(
    city: String,
    country: String,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = "$city, $country",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start
    )
}

@Composable
fun DateText(
    date: String,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = date,
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SunriseSunsetText(
    sunriseTime: String,
    sunsetTime: String,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.label_sunrise_sunset, sunriseTime, sunsetTime),
        style = MaterialTheme.typography.labelMedium,
        textAlign = TextAlign.Start
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun HistoryPreview() {
    WeatherAppTheme {
        HistoryContent(
            modifier = Modifier,
            screenState = rememberHistoryScreenState(
                weatherList = mutableStateOf(
                    mutableStateListOf(
                        Weather(
                            id = 0,
                            userId = "",
                            weatherId = 800,
                            weatherStatus = "Clear",
                            temperature = "32",
                            date = 1714099763,
                            timezone = 28800,
                            city = "Pasig",
                            country = "Philippines",
                            sunrise = 1714080926,
                            sunset = 1714126289
                        )
                    )
                )
            )
        )
    }
}