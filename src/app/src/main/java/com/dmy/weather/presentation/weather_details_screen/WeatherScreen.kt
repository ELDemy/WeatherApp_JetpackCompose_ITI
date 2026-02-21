package com.dmy.weather.presentation.weather_details_screen

import CurrentWeatherSection
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.weather_details_screen.components.DailyForecast
import com.dmy.weather.presentation.weather_details_screen.components.HourlyForecast
import com.dmy.weather.presentation.weather_details_screen.components.WeatherDetails
import org.koin.androidx.compose.koinViewModel

private const val TAG = "WeatherScreen"

@Composable
fun WeatherScreen(
    navController: NavController,
    appbarViewModel: AppbarViewModel,
    modifier: Modifier,
    location: LocationDetails? = null,
) {
    Log.i(TAG, "WeatherScreenLocation is: $location")
    val viewModel: WeatherVM = koinViewModel<WeatherVM>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settingsState by viewModel.settingsState.collectAsState()

    LaunchedEffect(uiState.currentWeather.data?.bg) {
        appbarViewModel.updateBackground(uiState.currentWeather.data?.bg)
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        CurrentWeatherSection(
            state = uiState.currentWeather,
            dayForecast = uiState.dailyForecast.data,
            unit = settingsState.unit!!
        )

        HourlyForecast(state = uiState.hourlyForecast, uiState.currentWeather.data)

        WeatherDetails(state = uiState.currentWeather, settingsState.unit!!)

        DailyForecast(state = uiState.dailyForecast)
    }
}

