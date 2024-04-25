package com.stratpoint.weatherapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stratpoint.weatherapp.navigation.HomeNavGraph
import com.stratpoint.weatherapp.navigation.HomeScreen
import com.stratpoint.weatherapp.navigation.navigateSingleTopTo

@Composable
fun HomeScreen(
    navigateToAuth: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    if (viewModel.isUserLoggedIn) {
        HomeContent(
            navigateToAuth = navigateToAuth
        )
    } else {
        LaunchedEffect(Unit) {
            navigateToAuth()
        }
    }

}


@Composable
fun HomeContent(
    navigateToAuth: () -> Unit
) {

    //scaffold
    val homeNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(homeNavController = homeNavController) }
    ) {
        HomeNavGraph(
            modifier = Modifier.padding(paddingValues = it),
            homeNavController = homeNavController,
            navigateToAuth = navigateToAuth
        )
    }

}

@Composable
fun BottomNavBar(homeNavController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {

        val screens = listOf(
            HomeScreen.CurrentWeather,
            HomeScreen.History
        )

        val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            val isTabSelected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = stringResource(
                            id = screen.tabName
                        )
                    )
                },
                label = { Text(text = stringResource(id = screen.tabName)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surfaceTint,
                    selectedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                selected = isTabSelected,
                onClick = {
                    homeNavController.navigateSingleTopTo(screen.route)
                }
            )
        }
    }
}