package com.dmy.weather.presentation.weather_details_screen

import CurrentWeatherSection
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dmy.weather.R.color
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.components.CustomFAB
import com.dmy.weather.presentation.weather_details_screen.components.DailyForecast
import com.dmy.weather.presentation.weather_details_screen.components.HourlyForecast
import com.dmy.weather.presentation.weather_details_screen.components.WarningBox
import com.dmy.weather.presentation.weather_details_screen.components.WeatherDetails
import kotlinx.coroutines.launch
import org.koin.compose.getKoin

private const val TAG = "WeatherScreen"

@Composable
fun WeatherScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    appbarViewModel: AppbarViewModel,
    location: LocationDetails,
    showFavFab: Boolean,
    modifier: Modifier,
    warning: String? = null,
    onRefresh: (() -> Unit)? = null,
    onWarningClick: (() -> Unit)? = null,
) {
    val scope = rememberCoroutineScope()
    val viewModel =
        viewModel<WeatherVM>(
            factory = WeatherVMFactory(
                getKoin().get<WeatherRepository>(),
                getKoin().get<CityRepository>(),
                getKoin().get<SettingsRepository>()
            )
        )
    Log.i(TAG, "WeatherScreenLocation is: $location")

    LaunchedEffect(location) { viewModel.loadWeatherData(location) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settingsState by viewModel.settingsState.collectAsState()

    LaunchedEffect(uiState.currentWeather.data?.bg) {
        appbarViewModel.updateBackground(uiState.currentWeather.data?.bg)
    }

    val isRefreshing = uiState.currentWeather.isLoading

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh ?: { viewModel.loadWeatherData(location) },
        modifier = modifier
    ) {
        Box(Modifier.fillMaxSize()) {
            CompositionLocalProvider(LocalContentColor provides colorResource(color.text_white)) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    CurrentWeatherSection(
                        state = uiState.currentWeather,
                        dayForecast = uiState.dailyForecast.data,
                        unit = settingsState.unit!!
                    )

                    if (warning != null) {
                        WarningBox(
                            warning = warning,
                            onButtonClick = onWarningClick
                        )
                    }
                    HourlyForecast(state = uiState.hourlyForecast, uiState.currentWeather.data)

                    WeatherDetails(state = uiState.currentWeather, settingsState.unit!!)

                    DailyForecast(state = uiState.dailyForecast)
                }
            }

            if (uiState.currentWeather.data != null && showFavFab) {
                val weather: WeatherModel = uiState.currentWeather.data!!
                CustomFAB(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 15.dp),
                    icon = Icons.Default.Favorite,
                    onFabClick = {
                        viewModel.addToFav(weather.cityName)

                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            
                            snackbarHostState.showSnackbar(
                                message = "${weather.cityName} added to Favorites",
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                )

            }
        }
    }
}