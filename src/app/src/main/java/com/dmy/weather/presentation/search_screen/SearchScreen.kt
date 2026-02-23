package com.dmy.weather.presentation.search_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.my_app.NavScreens

private const val TAG = "SearchScreen"

@Composable
fun SearchScreen(navController: NavController, modifier: Modifier) {

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<LocationDetails?>("picked_location", null)
            ?.collect { location ->
                Log.i(TAG, "SearchScreen: Location received is $location")
                if (location != null) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<LocationDetails>("picked_location")

                    navController.navigate(
                        NavScreens.WeatherScreen(
                            lat = location.lat,
                            long = location.long
                        )
                    )
                }
            }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                navController.navigate(NavScreens.LocationPickerScreen)
            }
        ) {
            Text("Search Location")
        }
    }
}
