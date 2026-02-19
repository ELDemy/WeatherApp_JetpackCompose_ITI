package com.dmy.weather.presentation.settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.presentation.settings_screen.components.AlertSettingCard
import com.dmy.weather.presentation.settings_screen.components.LanguageSettings
import com.dmy.weather.presentation.settings_screen.components.LocationSettings
import com.dmy.weather.presentation.settings_screen.components.UnitSystemSettings
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel: SettingsVM = koinViewModel()

    val uiState by viewModel.settingsState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LocationSettings(viewModel, uiState.locationMode ?: LocationMode.GPS)

        UnitSystemSettings(viewModel, uiState.unit ?: UnitSystem.METRIC)

        LanguageSettings(uiState.lang ?: AppLanguage.DEFAULT, viewModel, navController)

        AlertSettingCard(viewModel, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview(modifier: Modifier = Modifier) {
    SettingsScreen(rememberNavController())
}