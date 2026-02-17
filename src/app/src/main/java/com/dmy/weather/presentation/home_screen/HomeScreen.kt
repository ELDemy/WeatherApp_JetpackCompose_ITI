package com.dmy.weather.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.data.model.WeatherModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeVM =
        viewModel(factory = HomeVMFactory(LocalContext.current))

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .background(color = colorResource(R.color.blue_primary))
                .padding(vertical = 32.dp, horizontal = 24.dp)
                .fillMaxWidth(),

            ) {
            CurrentWeatherSection(state = uiState.currentWeather)
        }
    }
}


@Composable
fun CurrentWeatherSection(state: UiState<WeatherModel>) {
    when {
        state.data != null -> {
            Column() {
                Text("City: ${state.data.cityName}")
                Text("Temp: ${state.data.temperature}°C")
            }
        }

        state.isLoading -> CircularProgressIndicator()

        state.error != null -> Text("Error: ${state.error}")

        else -> Text("No Data")
    }
}