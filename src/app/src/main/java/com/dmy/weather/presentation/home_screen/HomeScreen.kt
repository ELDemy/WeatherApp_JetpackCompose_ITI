package com.dmy.weather.presentation.home_screen

import CurrentWeatherSection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dmy.weather.presentation.home_screen.components.WeatherDetails

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {
    val viewModel: HomeVM =
        viewModel(factory = HomeVMFactory(LocalContext.current))

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CurrentWeatherSection(state = uiState.currentWeather)
        WeatherDetails(state = uiState.currentWeather)
    }
}

