package com.dmy.weather

import android.app.Application
import com.dmy.weather.di.dataModule
import com.dmy.weather.di.networkModule
import com.dmy.weather.di.viewModelModule
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
            Places.initializeWithNewPlacesApiEnabled(applicationContext, BuildConfig.MAPS_API_KEY)
        }

        startKoin {
            androidContext(this@WeatherApp)
            modules(
                networkModule,
                dataModule,
                viewModelModule,
            )
        }
    }
}