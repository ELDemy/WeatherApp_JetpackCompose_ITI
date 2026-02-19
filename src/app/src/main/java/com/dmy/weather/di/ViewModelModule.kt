package com.dmy.weather.di

import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository
import com.dmy.weather.presentation.home_screen.HomeVM
import com.dmy.weather.presentation.settings_screen.SettingsVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SettingsVM(get<SettingsRepository>()) }
    viewModel { HomeVM(get<WeatherRepository>(), get<SettingsRepository>()) }
}