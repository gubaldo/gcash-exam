package com.stratpoint.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(rootNavController: NavHostController) {

    NavHost(
        route = Graph.Root.route,
        navController = rootNavController,
        startDestination = Graph.Auth.route,
    ) {

        authNavGraph(
            navController = rootNavController
        )

    }

}

sealed class Graph(val route: String) {
    object Root : Graph("root_nav_host")
    object Auth : Graph("auth_nav_graph")
}