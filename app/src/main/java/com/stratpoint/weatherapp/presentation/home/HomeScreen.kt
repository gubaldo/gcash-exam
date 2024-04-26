package com.stratpoint.weatherapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.navigation.HomeNavGraph
import com.stratpoint.weatherapp.navigation.HomeScreen
import com.stratpoint.weatherapp.navigation.navigateSingleTopTo
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.views.dialog.ConfirmationAlertDialog

@Composable
fun HomeScreen(
    navigateToAuth: () -> Unit,
    screenState: HomeScreenState = rememberHomeScreenState(),
    viewModel: HomeViewModel = hiltViewModel()
) {

    InitializeScreenState(screenState, viewModel)

    if (viewModel.isUserLoggedIn) {
        HomeContent(
            screenState = screenState,
            onClickConfirmLogout = {
                screenState.showConfirmLogout.value = false
                viewModel.logout()
            },
            onDismissErrorDialog = viewModel::dismissErrorDialog
        )
    } else {
        LaunchedEffect(Unit) {
            navigateToAuth()
        }
    }

    LaunchedEffect(screenState.logoutSuccess.value) {
        if (screenState.logoutSuccess.value) {
            navigateToAuth()
        }
    }

}


@Composable
fun HomeContent(
    screenState: HomeScreenState,
    onClickConfirmLogout: () -> Unit,
    onDismissErrorDialog: () -> Unit
) {

    //scaffold
    val homeNavController = rememberNavController()
    Scaffold(
        topBar = {
            HomeTopBar(screenState)
        },
        bottomBar = { BottomNavBar(homeNavController = homeNavController) }
    ) {
        HomeNavGraph(
            modifier = Modifier.padding(paddingValues = it),
            homeNavController = homeNavController
        )

        LogoutConfirmationDialog(
            showDialog = screenState.showConfirmLogout.value,
            onClickLogout = {
                onClickConfirmLogout()
            },
            dismissDialog = {
                screenState.showConfirmLogout.value = false
            }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(screenState: HomeScreenState) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = {
                screenState.showConfirmLogout.value = !screenState.showConfirmLogout.value
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_logout),
                    contentDescription = null
                )
            }

        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.surfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Composable
fun BottomNavBar(homeNavController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary
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
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                ),
                selected = isTabSelected,
                onClick = {
                    homeNavController.navigateSingleTopTo(screen.route)
                }
            )
        }
    }
}

@Composable
fun LogoutConfirmationDialog(
    showDialog: Boolean,
    onClickLogout: () -> Unit,
    dismissDialog: () -> Unit
) {
    ConfirmationAlertDialog(
        showDialog = showDialog,
        message = stringResource(id = R.string.confirm_logout),
        positiveButtonLabel = stringResource(id = R.string.label_logout),
        negativeButtonLabel = stringResource(id = R.string.label_cancel),
        positiveButtonClick = { onClickLogout() },
        negativeButtonClick = { dismissDialog() }
    )
}

@Composable
private fun InitializeScreenState(screenState: HomeScreenState, viewModel: HomeViewModel) {
    screenState.logoutSuccess.value = viewModel.logoutSuccess.collectAsState().value
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    WeatherAppTheme {
        HomeContent(
            screenState = rememberHomeScreenState(),
            onClickConfirmLogout = {},
            onDismissErrorDialog = {}
        )
    }
}