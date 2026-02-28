package com.dmy.weather.presentation.app_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dmy.weather.R
import com.dmy.weather.presentation.my_app.NavScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    appbarViewModel: AppbarViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lifecycleState by navBackStackEntry
        ?.lifecycle
        ?.currentStateFlow
        ?.collectAsState() ?: return

    var currentDestination by remember { mutableStateOf(navBackStackEntry?.destination) }

    LaunchedEffect(lifecycleState) {
        if (lifecycleState >= Lifecycle.State.STARTED) {
            currentDestination = navBackStackEntry?.destination
        }
    }
    when {
        currentDestination?.hasRoute<NavScreens.HomeScreen>() == true -> {
            HomeTopBar(navController, scrollBehavior, appbarViewModel.background)
        }

        currentDestination?.hasRoute<NavScreens.WeatherScreen>() == true -> {
            WeatherTopBar(navController, scrollBehavior, appbarViewModel.background)
        }

        currentDestination?.hasRoute<NavScreens.SettingsScreen>() == true -> {
            DefaultTopBar(
                navController,
                stringResource(R.string.Settings),
                icon = Icons.Filled.Settings,
                scrollBehavior
            )
        }

        currentDestination?.hasRoute<NavScreens.FavoritesScreen>() == true -> {
            DefaultTopBar(
                navController,
                stringResource(R.string.Saved_Locations),
                icon = Icons.Filled.Favorite,
                scrollBehavior
            )
        }

        currentDestination?.hasRoute<NavScreens.LocationSearchScreen>() == true -> {
            DefaultTopBar(
                navController,
                stringResource(R.string.Select_Location),
                icon = Icons.Filled.LocationOn,
                scrollBehavior
            )
        }

        currentDestination?.hasRoute<NavScreens.AlertsScreen>() == true -> {
            DefaultTopBar(
                navController,
                stringResource(R.string.Alerts),
                icon = Icons.Filled.Alarm,
                scrollBehavior
            )
        }

        currentDestination?.hasRoute<NavScreens.SplashScreen>() == true -> {

        }

        else -> {
            ShrinkAppBar()
        }
    }
}

