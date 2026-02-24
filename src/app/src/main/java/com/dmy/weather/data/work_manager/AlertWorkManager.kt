package com.dmy.weather.data.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmy.weather.data.repo.WeatherRepository
import org.koin.java.KoinJavaComponent.inject

private const val TAG = "WeatherWorkManager"

class AlertWorkManager(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val weatherRepository: WeatherRepository by inject(WeatherRepository::class.java)

    override suspend fun doWork(): Result {
        return weatherRepository.getAlertWeather()
            .onSuccess { (notificationWeather, minutes) ->
                val triggerTime = notificationWeather.dt * 1000 - (minutes * 60 * 1000)
                Log.i(TAG, "doWork: at $triggerTime")
                AlarmScheduler.scheduleNotificationAt(context, triggerTime, notificationWeather)
            }
            .fold(
                onSuccess = { Result.success() },
                onFailure = { Result.retry() }
            )
    }

}