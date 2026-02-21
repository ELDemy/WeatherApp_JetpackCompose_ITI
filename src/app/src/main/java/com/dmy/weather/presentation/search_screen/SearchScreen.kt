package com.dmy.weather.presentation.search_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dmy.weather.presentation.map_screen.LocationPickerScreen

private const val TAG = "SearchScreen"

@Composable
fun SearchScreen(navController: NavController, modifier: Modifier) {
    LocationPickerScreen(navController)
}
