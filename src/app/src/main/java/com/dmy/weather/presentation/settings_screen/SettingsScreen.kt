package com.dmy.weather.presentation.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {

    Column() {
        
        Text(text = "Settings")

    }
}