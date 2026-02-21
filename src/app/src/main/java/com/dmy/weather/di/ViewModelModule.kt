package com.dmy.weather.di

import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.settings_screen.SettingsVM
import com.dmy.weather.presentation.weather_details_screen.WeatherVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AppbarViewModel() }
    viewModel { SettingsVM(get<SettingsRepository>()) }
    viewModel { WeatherVM(get<WeatherRepository>(), get<SettingsRepository>()) }

}