@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.my_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import com.dmy.weather.presentation.app_bar.AppBar
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.home_screen.HomeScreen
import com.dmy.weather.presentation.language_selection_screen.LanguageSelectionScreen
import com.dmy.weather.presentation.location_picker_screen.LocationPickerScreen
import com.dmy.weather.presentation.search_screen.SearchScreen
import com.dmy.weather.presentation.settings_screen.SettingsScreen
import com.dmy.weather.presentation.weather_details_screen.WeatherScreen
import org.koin.androidx.compose.koinViewModel

private const val TAG = "WeatherApp"

@Composable
fun MyApp() {
    val appbarViewModel = koinViewModel<AppbarViewModel>()
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white))
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
            startDestination = NavScreens.HomeScreen,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<NavScreens.HomeScreen> {
                HomeScreen(navController, appbarViewModel, modifier)
            }

            composable<NavScreens.WeatherScreen> { backStackEntry ->
                val screen = backStackEntry.toRoute<NavScreens.WeatherScreen>()
                val locationDetails = LocationDetails(screen.city, screen.long, screen.lat)
                WeatherScreen(
                    navController,
                    appbarViewModel,
                    locationDetails,
                    modifier,
                )
            }
            composable<NavScreens.SettingsScreen> {
                SettingsScreen(navController, modifier)
            }

            composable<NavScreens.LocationPickerScreen> {
                LocationPickerScreen(navController, modifier)
            }
            composable<NavScreens.LanguageSelectionScreen> {
                LanguageSelectionScreen(navController, modifier)
            }
            composable<NavScreens.SearchScreen> {
                SearchScreen(navController, modifier)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}