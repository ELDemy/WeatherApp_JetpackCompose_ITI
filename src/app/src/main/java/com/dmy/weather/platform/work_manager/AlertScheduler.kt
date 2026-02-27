package com.dmy.weather.platform.work_manager

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val TAG = "AlertScheduler"

object AlertScheduler {
    fun scheduleWeatherCheck(context: Context) {
        Log.i(TAG, "scheduleWeatherCheck: ")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val weatherRequest =
            PeriodicWorkRequestBuilder<AlertWorkManager>(
//                1, TimeUnit.HOURS
                20, TimeUnit.MINUTES
            ).setConstraints(constraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "weather_check",
            ExistingPeriodicWorkPolicy.KEEP,
            weatherRequest
        )
    }
}