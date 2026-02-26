package com.dmy.weather.di

import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSource
import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSourceImpl
import com.dmy.weather.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
    single<SettingsDataSource> { SettingsDataSourceImpl(androidContext()) }

    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().alertDao() }
    single { get<AppDatabase>().favLocationsDao() }
}