package com.stratpoint.weatherapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stratpoint.weatherapp.presentation.auth.login.presentation.LoginScreen
import com.stratpoint.weatherapp.presentation.auth.register.presentation.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.Auth.route,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreen.Register.route) {
            RegisterScreen(navController = navController)
        }
    }

}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login_screen")
    object Register : AuthScreen("register_screen")
}