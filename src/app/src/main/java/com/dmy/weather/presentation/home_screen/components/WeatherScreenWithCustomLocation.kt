package com.dmy.weather.presentation.home_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.components.LocationFAB
import com.dmy.weather.presentation.weather_details_screen.WeatherScreen


@Composable
fun WeatherScreenWithCustomLocation(
    navController: NavController,
    appbarViewModel: AppbarViewModel,
    location: LocationDetails,
    modifier: Modifier,
    warning: String? = null,
    onFabClick: () -> Unit,
    onRefresh: (() -> Unit),
    onWarningClick: (() -> Unit)? = null,
) {
    Box(Modifier.fillMaxSize()) {
        WeatherScreen(
            navController = navController,
            appbarViewModel = appbarViewModel,
            location = location,
            warning = warning,
            onWarningClick = onWarningClick,
            modifier = modifier,
            onRefresh = onRefresh,
        )

        LocationFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            onFabClick = onFabClick
        )
    }

}
