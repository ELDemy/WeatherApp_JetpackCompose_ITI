package com.dmy.weather.presentation.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dmy.weather.data.repo.WeatherRepo

@Composable
fun HomeScreen(navController: NavController) {
    Text(
        text = "Hello",
        modifier = Modifier.clickable(onClick = {
            WeatherRepo().getGeocodingCityInfoByCity("Cairo")

        })
    )
}