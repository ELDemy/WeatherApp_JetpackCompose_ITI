package com.dmy.weather.platform.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmy.weather.data.mapper.getTimeInFullDate
import com.dmy.weather.data.repo.WeatherRepository
import com.dmy.weather.platform.notification.NotificationBuilder
import org.koin.java.KoinJavaComponent.inject

private const val TAG = "WeatherWorkManager"

class AlertWorkManager(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val weatherRepository: WeatherRepository by inject(WeatherRepository::class.java)

    override suspend fun doWork(): Result {
        return weatherRepository.getAlertWeather()
            .onSuccess { (notificationWeather, alert) ->
                val triggerTime = notificationWeather.dt * 1000 - (alert.time * 60 * 1000)
                Log.i(TAG, "doWork: at $triggerTime")
                NotificationBuilder.showTestNotification(
                    context,
                    "Scheduled work manager fired",
                    "Alarm Will fire on ${(triggerTime / 1000).getTimeInFullDate()}"
                )
                AlarmScheduler.scheduleNotificationAt(
                    context,
                    triggerTime,
                    notificationWeather,
                    alert
                )
            }
            .fold(
                onSuccess = { Result.success() },
                onFailure = { Result.retry() }
            )
    }

}