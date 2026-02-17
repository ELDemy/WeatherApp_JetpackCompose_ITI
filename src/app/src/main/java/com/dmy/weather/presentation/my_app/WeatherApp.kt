package com.dmy.weather.presentation.my_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dmy.weather.presentation.home_screen.HomeScreen

@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreens.HomeScreen,
        modifier = modifier
    ) {
        composable<NavScreens.HomeScreen> {
            HomeScreen(navController)
        }

    }

}

