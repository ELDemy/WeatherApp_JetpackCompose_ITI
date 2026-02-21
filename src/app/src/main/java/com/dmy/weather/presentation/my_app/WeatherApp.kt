@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.my_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dmy.weather.R
import com.dmy.weather.presentation.home_screen.HomeScreen
import com.dmy.weather.presentation.home_screen.HomeVM
import com.dmy.weather.presentation.language_selection_screen.LanguageSelectionScreen
import com.dmy.weather.presentation.my_app.top_bar.AppBar
import com.dmy.weather.presentation.search_screen.SearchScreen
import com.dmy.weather.presentation.settings_screen.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val homeVM: HomeVM = koinViewModel()
    val uiState by homeVM.uiState.collectAsStateWithLifecycle()

    val modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white))

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBar(
                navController,
                scrollBehavior,
                bg = uiState.currentWeather.data?.bg,
            )
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreens.SearchScreen,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<NavScreens.HomeScreen> {
                HomeScreen(navController, modifier)
            }
            composable<NavScreens.SettingsScreen> {
                SettingsScreen(navController, modifier)
            }
            composable<NavScreens.LanguageSelectionScreen> {
                LanguageSelectionScreen(navController, modifier)
            }
            composable<NavScreens.SearchScreen> {
                SearchScreen(navController, modifier)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    WeatherApp()
}