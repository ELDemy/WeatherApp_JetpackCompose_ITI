package com.dmy.weather.presentation.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.weather_details_screen.WeatherScreen

@Composable
fun HomeScreen(
    navController: NavController,
    appbarViewModel: AppbarViewModel,
    modifier: Modifier,
) {
    WeatherScreen(navController, appbarViewModel, modifier)
}