package com.dmy.weather.presentation.settings_screen.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.presentation.my_app.NavScreens
import com.dmy.weather.presentation.settings_screen.SettingsVM

@Composable
fun AlertSettingCard(viewModel: SettingsVM, navController: NavController) {
    CustomSettingItem(
        title = "Weather Alerts",
        subtitle = "Manage notifications and alarms",
        backgroundGradient = listOf(
            colorResource(R.color.warning_background),
            colorResource(R.color.warning_foreground),
        ),
        onClick = { navController.navigate(NavScreens.AlertsScreen) }
    ) {
        Icon(
            Icons.Filled.Notifications,
            contentDescription = "Alerts",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}