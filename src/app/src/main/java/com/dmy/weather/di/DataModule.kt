package com.dmy.weather.di

import com.dmy.weather.data.data_source.local.data_store.MyDataStore
import com.dmy.weather.data.data_source.remote.GeocodingRemoteDataSource
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.network.GeocodingService
import com.dmy.weather.data.network.WeatherService
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { MyDataStore(androidContext()) }
    single { WeatherRemoteDataSource(get<WeatherService>()) }
    single { GeocodingRemoteDataSource(get<GeocodingService>()) }
    single { SettingsRepository(get<MyDataStore>()) }
    single {
        WeatherRepository(
            get<WeatherRemoteDataSource>(),
            get<GeocodingRemoteDataSource>(),
            get<SettingsRepository>()
        )
    }
}