package com.stratpoint.weatherapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stratpoint.weatherapp.auth.login.presentation.LoginScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.Auth.route,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }
    }

}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login_screen")
}