package com.dmy.weather.presentation.home_screen

import CurrentWeatherSection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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
//    val viewModel: HomeVM =
//        viewModel(factory = HomeVMFactory(LocalContext.current))
    val viewModel: HomeVM = koinViewModel<HomeVM>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        CurrentWeatherSection(state = uiState.currentWeather, uiState.dailyForecast.data)
        HourlyForecast(state = uiState.hourlyForecast, uiState.currentWeather.data)
        DailyForecast(state = uiState.dailyForecast)
        WeatherDetails(state = uiState.currentWeather)
    }
}

