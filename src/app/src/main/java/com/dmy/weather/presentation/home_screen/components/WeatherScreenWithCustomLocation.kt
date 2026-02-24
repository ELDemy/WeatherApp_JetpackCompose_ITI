package com.dmy.weather.presentation.home_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.app_bar.AppbarViewModel
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

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(end = 16.dp, bottom = 48.dp)
                .size(56.dp),
            containerColor = colorResource(R.color.blue_primary),
            onClick = onFabClick,
            elevation = FloatingActionButtonDefaults.elevation(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "My Location",
                tint = colorResource(R.color.white),
            )
        }
    }

}


