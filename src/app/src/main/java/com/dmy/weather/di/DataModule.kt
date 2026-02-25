package com.dmy.weather.di

import com.dmy.weather.data.data_source.local.AlertsDataSource
import com.dmy.weather.data.data_source.local.CitiesDataSource
import com.dmy.weather.data.data_source.local.MyDataStore
import com.dmy.weather.data.data_source.remote.GeocodingRemoteDataSource
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.db.AppDatabase
import com.dmy.weather.data.network.GeocodingService
import com.dmy.weather.data.network.WeatherService
import com.dmy.weather.data.repo.AlertRepository
import com.dmy.weather.data.repo.CityRepository
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { MyDataStore(androidContext()) }

    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().alertDao() }
    single { get<AppDatabase>().favLocationsDao() }

    single { WeatherRemoteDataSource(get<WeatherService>()) }
    single { GeocodingRemoteDataSource(get<GeocodingService>()) }
    single { AlertsDataSource(get<AlertDao>()) }


    single { SettingsRepository(get<MyDataStore>()) }
    single { AlertRepository(get<AlertsDataSource>()) }

    single {
        CityRepository(
            get<GeocodingRemoteDataSource>(),
            get<CitiesDataSource>(),
        )
    }
    single {
        WeatherRepository(
            get<WeatherRemoteDataSource>(),
            get<SettingsRepository>(),
            get<AlertRepository>(),
            androidContext()
        )
    }
}