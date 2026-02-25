package com.dmy.weather.di

import com.dmy.weather.data.data_source.local.data_store.MyDataStore
import com.dmy.weather.data.data_source.remote.GeocodingRemoteDataSource
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.db.AppDatabase
import com.dmy.weather.data.network.GeocodingService
import com.dmy.weather.data.network.WeatherService
import com.dmy.weather.data.repo.LocationRepository
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { MyDataStore(androidContext()) }

    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().alertDao() }

    single { WeatherRemoteDataSource(get<WeatherService>()) }
    single { GeocodingRemoteDataSource(get<GeocodingService>()) }

    single { LocationRepository(get<GeocodingRemoteDataSource>()) }
    single {
        SettingsRepository(
            get<MyDataStore>(),
            get<AlertDao>()
        )
    }
    single {
        WeatherRepository(
            get<WeatherRemoteDataSource>(),
            get<SettingsRepository>(),
            androidContext()
        )
    }
}