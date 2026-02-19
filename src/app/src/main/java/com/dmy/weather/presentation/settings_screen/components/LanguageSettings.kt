package com.dmy.weather.presentation.settings_screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmy.weather.presentation.settings_screen.SettingsVM

@Composable
fun LanguageSettings(lang: String, viewModel: SettingsVM, navController: NavController) {
    CustomSettingItem(
        title = lang,
        subtitle = "Current language",
        backgroundGradient = listOf(Color(0xFFFEF3C7), Color(0xFFFEF3C7)),
        onClick = { /* navigate to Languages */ }
    ) {
        Text("🌐", fontSize = 24.sp)
    }
}