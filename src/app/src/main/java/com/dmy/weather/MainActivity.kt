package com.dmy.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dmy.weather.presentation.my_app.WeatherApp
import com.dmy.weather.presentation.settings_screen.SettingsVM
import com.dmy.weather.ui.theme.WeatherTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val settingsVM: SettingsVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                WeatherApp()
            }
        }
    }
}
