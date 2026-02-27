package com.dmy.weather.di

import com.dmy.weather.data.repo.alert_repo.AlertRepository
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import com.dmy.weather.presentation.alerts_screen.AlertsVM
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.favorites_screen.FavoritesVM
import com.dmy.weather.presentation.home_screen.HomeVM
import com.dmy.weather.presentation.location_search_screen.component.search_field.SearchVM
import com.dmy.weather.presentation.settings_screen.SettingsVM
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.dsl.viewModel
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
    viewModel {
        FavoritesVM(
            get<CityRepository>(),
            get<WeatherRepository>()
        )
    }
}