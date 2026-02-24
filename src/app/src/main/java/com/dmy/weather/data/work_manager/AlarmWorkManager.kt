package com.dmy.weather.data.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmy.weather.data.repo.WeatherRepository
import org.koin.java.KoinJavaComponent.inject

private const val TAG = "WeatherWorkManager"

class AlarmWorkManager(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val weatherRepository: WeatherRepository by inject(WeatherRepository::class.java)

    override suspend fun doWork(): Result {
        return weatherRepository.getCurrentWeather()
            .onSuccess { weather ->
                val temp = weather.temperature ?: return@onSuccess
                if (temp > 0) {
                    val triggerTime = System.currentTimeMillis() + (2 * 60 * 1000)
                    AlarmScheduler.scheduleNotificationAt(context, triggerTime, weather)
                }
            }
            .fold(
                onSuccess = { Result.success() },
                onFailure = { Result.retry() }
            )
    }

}