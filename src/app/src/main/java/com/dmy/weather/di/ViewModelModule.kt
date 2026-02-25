package com.dmy.weather.di

import com.dmy.weather.data.repo.AlertRepository
import com.dmy.weather.data.repo.CityRepository
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.presentation.alerts_screen.AlertsVM
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.home_screen.HomeVM
import com.dmy.weather.presentation.search_screen.SearchVM
import com.dmy.weather.presentation.settings_screen.SettingsVM
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(FlowPreview::class)
val viewModelModule = module {
    viewModel { AppbarViewModel() }
    viewModel { SettingsVM(get<SettingsRepository>()) }
    viewModel { HomeVM(get<SettingsRepository>()) }
    viewModel { SearchVM(get<CityRepository>()) }
    viewModel {
        AlertsVM(
            get<AlertRepository>(),
            get<SettingsRepository>()
        )
    }
}