package com.dmy.weather.data.work_manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


object WorkManagerScheduler {
    fun scheduleWeatherCheck(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val weatherRequest =
            PeriodicWorkRequestBuilder<AlarmWorkManager>(
//                1, TimeUnit.HOURS
                15, TimeUnit.MINUTES
            ).setConstraints(constraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "weather_check",
            ExistingPeriodicWorkPolicy.KEEP,
            weatherRequest
        )
    }
}