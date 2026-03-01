package com.dmy.weather.platform.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmy.weather.data.repo.alert_repo.AlertRepository
import org.koin.java.KoinJavaComponent.inject

private const val TAG = "WeatherWorkManager"

class AlertWorkManager(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val alertRepository: AlertRepository by inject(AlertRepository::class.java)

    override suspend fun doWork(): Result {
        Log.i(TAG, "doWork: at ${System.currentTimeMillis()}")
        return alertRepository.createAlarms()
            .fold(
                onSuccess = { Result.success() },
                onFailure = { Result.retry() }
            )
    }

}