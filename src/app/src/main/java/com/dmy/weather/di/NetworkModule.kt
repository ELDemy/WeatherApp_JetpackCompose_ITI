package com.dmy.weather.di

import com.dmy.weather.data.network.WeatherNetwork
import com.dmy.weather.data.repo.SettingsRepository
import org.koin.dsl.module

val networkModule = module {
    single { WeatherNetwork(get<SettingsRepository>()) }
    single { get<WeatherNetwork>().weatherService }
    single { get<WeatherNetwork>().geocodingService }
}