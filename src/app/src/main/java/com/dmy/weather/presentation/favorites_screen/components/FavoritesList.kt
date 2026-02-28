package com.dmy.weather.presentation.favorites_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.presentation.components.CustomFAB
import com.dmy.weather.presentation.my_app.NavScreens

@Composable
fun FavoritesList(
    navController: NavController,
    cities: List<CityModel>,
    modifier: Modifier = Modifier
) {
    Box(Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cities, key = { it.name + it.longitude + it.latitude }) { city ->
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

        CustomFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            icon = Icons.Default.Add,
            onFabClick = {
                navController.navigate(NavScreens.LocationSearchScreen(popOnLocationPicked = "0"))
            }
        )
    }
}
