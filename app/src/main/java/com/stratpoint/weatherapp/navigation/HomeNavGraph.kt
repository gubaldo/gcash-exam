package com.stratpoint.weatherapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.presentation.home.current_weather.CurrentWeatherScreen
import com.stratpoint.weatherapp.presentation.home.history.HistoryScreen

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    homeNavController: NavHostController,
    navigateToAuth: () -> Unit
) {

    //Create new NavHost for BottomNavBar
    NavHost(
        navController = homeNavController,
        route = Graph.Home.route,
        startDestination = HomeScreen.CurrentWeather.route
    ) {

        composable(route = HomeScreen.CurrentWeather.route) {
            CurrentWeatherScreen(
                modifier = modifier,
                navigateToAuth
            )
        }

        composable(route = HomeScreen.History.route) {
            HistoryScreen(modifier, navigateToAuth)
        }

    }
}

sealed class HomeScreen(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val tabName: Int
) {
    object CurrentWeather : HomeScreen(
        "current_weather_screen",
        R.drawable.ic_cloud,
        R.string.label_weather_today
    )

    object History : HomeScreen(
        "weather_history_screen",
        R.drawable.ic_history,
        R.string.label_weather_history
    )
}