package com.dmy.weather.di

import com.dmy.weather.data.data_source.local.alerts_data_source.AlertsDataSource
import com.dmy.weather.data.data_source.local.alerts_data_source.AlertsDataSourceImpl
import com.dmy.weather.data.data_source.local.cities_data_source.CitiesDataSource
import com.dmy.weather.data.data_source.local.cities_data_source.CitiesDataSourceImpl
import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSource
import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSourceImpl
import com.dmy.weather.data.data_source.remote.geocoding_data_source.GeocodingRemoteDataSource
import com.dmy.weather.data.data_source.remote.geocoding_data_source.GeocodingRemoteDataSourceImpl
import com.dmy.weather.data.data_source.remote.weather_data_source.WeatherRemoteDataSource
import com.dmy.weather.data.data_source.remote.weather_data_source.WeatherRemoteDataSourceImpl
import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.db.AppDatabase
import com.dmy.weather.data.db.FavLocationsDao
import com.dmy.weather.data.network.GeocodingService
import com.dmy.weather.data.network.WeatherService
import com.dmy.weather.data.repo.alert_repo.AlertRepository
import com.dmy.weather.data.repo.alert_repo.AlertRepositoryImpl
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.city_repo.CityRepositoryImpl
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepositoryImpl
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<SettingsDataSource> { SettingsDataSourceImpl(androidContext()) }

    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().alertDao() }
    single { get<AppDatabase>().favLocationsDao() }

    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get<WeatherService>()) }
    single<GeocodingRemoteDataSource> { GeocodingRemoteDataSourceImpl(get<GeocodingService>()) }
    single<AlertsDataSource> { AlertsDataSourceImpl(get<AlertDao>()) }
    single<CitiesDataSource> { CitiesDataSourceImpl(get<FavLocationsDao>()) }

    single<SettingsRepository> { SettingsRepositoryImpl(get<SettingsDataSource>()) }
    single<AlertRepository> { AlertRepositoryImpl(get<AlertsDataSource>()) }

    single<CityRepository> {
        CityRepositoryImpl(
            get<GeocodingRemoteDataSource>(),
            get<CitiesDataSource>(),
        )
    }
    
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            get<WeatherRemoteDataSource>(),
            get<SettingsRepository>(),
            get<AlertRepository>(),
            androidContext()
        )
    }
}