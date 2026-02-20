package com.dmy.weather.presentation.home_screen

import CurrentWeatherSection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dmy.weather.presentation.home_screen.components.DailyForecast
import com.dmy.weather.presentation.home_screen.components.HourlyForecast
import com.dmy.weather.presentation.home_screen.components.WeatherDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {
    val viewModel: HomeVM = koinViewModel<HomeVM>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settingsState by viewModel.settingsState.collectAsState()

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

