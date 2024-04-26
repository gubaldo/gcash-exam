package com.stratpoint.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stratpoint.weatherapp.presentation.home.HomeScreen
import com.stratpoint.weatherapp.presentation.home.current_weather.CurrentWeatherScreen

@Composable
fun RootNavGraph(rootNavController: NavHostController) {

    NavHost(
        route = Graph.Root.route,
        navController = rootNavController,
        startDestination = Graph.Home.route,
    ) {

        composable(route = Graph.Home.route) {
            HomeScreen(navigateToAuth = {
                rootNavController.navigate(Graph.Auth.route) {
                    popUpTo(Graph.Home.route) { inclusive = true }
                }
            })
        }

        authNavGraph(
            navController = rootNavController
        )

    }

}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

sealed class Graph(val route: String) {
    object Root : Graph("root_nav_host")
    object Home : Graph("home_nav_host")
    object Auth : Graph("auth_nav_graph")
}