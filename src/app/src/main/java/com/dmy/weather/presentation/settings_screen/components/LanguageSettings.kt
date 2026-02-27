package com.dmy.weather.presentation.settings_screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.presentation.my_app.NavScreens
import com.dmy.weather.presentation.settings_screen.SettingsVM

@Composable
fun LanguageSettings(lang: AppLanguage, viewModel: SettingsVM, navController: NavController) {
    CustomSettingItem(
        title = lang.displayName,
        subtitle = stringResource(R.string.Current_language),
        backgroundGradient = listOf(Color(0xFFFEF3C7), Color(0xFFFEF3C7)),
        onClick = { navController.navigate(NavScreens.LanguageSelectionScreen) }
    ) {
        Text("🌐", fontSize = 24.sp)
    }
}
