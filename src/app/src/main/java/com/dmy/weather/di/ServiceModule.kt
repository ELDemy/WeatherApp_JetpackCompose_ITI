package com.dmy.weather.di

import com.dmy.weather.platform.services.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serviceModule = module {
    single { LocationService(androidContext()) }
}