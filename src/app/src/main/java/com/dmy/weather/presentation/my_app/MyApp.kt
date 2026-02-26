@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.my_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dmy.weather.R
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.alerts_screen.AlertsScreen
import com.dmy.weather.presentation.app_bar.AppBar
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.favorites_screen.FavoritesScreen
import com.dmy.weather.presentation.home_screen.HomeScreen
import com.dmy.weather.presentation.language_selection_screen.LanguageSelectionScreen
import com.dmy.weather.presentation.location_picker_screen.LocationPickerScreen
import com.dmy.weather.presentation.search_screen.SearchScreen
import com.dmy.weather.presentation.settings_screen.SettingsScreen
import com.dmy.weather.presentation.splash_screen.SplashScreen
import com.dmy.weather.presentation.weather_details_screen.WeatherScreen
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.koinViewModel

private const val TAG = "WeatherApp"

@OptIn(FlowPreview::class)
@Composable
fun MyApp() {
    val appbarViewModel = koinViewModel<AppbarViewModel>()
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    val modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white))
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(snackbarData = data)
            }
        },
        topBar = {
            AppBar(
                navController,
                scrollBehavior,
                appbarViewModel,
            )
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreens.SplashScreen,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<NavScreens.SplashScreen> {
                SplashScreen(navController, modifier)
            }

            composable<NavScreens.HomeScreen> {
                HomeScreen(navController, appbarViewModel, snackbarHostState, modifier)
            }

            composable<NavScreens.WeatherScreen> { backStackEntry ->
                val screen = backStackEntry.toRoute<NavScreens.WeatherScreen>()
                val locationDetails = LocationDetails(screen.city, screen.long, screen.lat)
                WeatherScreen(
                    navController,
                    appbarViewModel,
                    locationDetails,
                    showFavFab = true,
                    modifier,
                )
            }

            composable<NavScreens.LocationPickerScreen> { backStackEntry ->
                val screen = backStackEntry.toRoute<NavScreens.LocationPickerScreen>()
                var locationDetails: LocationDetails? = null
                if (screen.lat != null && screen.long != null) {
                    locationDetails = LocationDetails(long = screen.long, lat = screen.lat)
                }
                LocationPickerScreen(navController, modifier, locationDetails)
            }

            composable<NavScreens.FavoritesScreen> {
                FavoritesScreen(navController, modifier)
            }

            composable<NavScreens.SearchScreen> {
                SearchScreen(navController, modifier)
            }

            composable<NavScreens.SettingsScreen> {
                SettingsScreen(navController, modifier)
            }

            composable<NavScreens.LanguageSelectionScreen> {
                LanguageSelectionScreen(navController, modifier)
            }

            composable<NavScreens.AlertsScreen> {
                AlertsScreen(modifier)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}