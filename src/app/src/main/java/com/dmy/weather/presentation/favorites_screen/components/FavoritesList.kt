package com.dmy.weather.presentation.favorites_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.presentation.my_app.NavScreens

@Composable
fun FavoritesList(
    navController: NavController,
    cities: List<CityModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cities) { city ->
            FavoriteCard(
                city = city,
                onClick = {
                    navController.navigate(
                        NavScreens.WeatherScreen(
                            long = city.longitude.toString(), lat = city.latitude.toString()
                        )
                    )
                }
            )
        }
    }
}
