package com.dmy.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.text.intl.Locale
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.presentation.my_app.WeatherApp
import com.dmy.weather.presentation.settings_screen.SettingsVM
import com.dmy.weather.ui.theme.WeatherTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val settingsVM: SettingsVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsVM.settingsState.collect { settings ->
                    applyLocale(settings.lang?.apiCode ?: AppLanguage.DEFAULT.apiCode)
                }
            }
        }
        setContent {
            WeatherTheme {
                WeatherApp()
            }
        }
    }

    private fun applyLocale(lang: String) {
        val locale = Locale(lang)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(lang)
        )
    }
}
