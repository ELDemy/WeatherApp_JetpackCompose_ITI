package com.dmy.weather.data.repo.alert_repo

import android.content.Context
import android.util.Log
import com.dmy.weather.data.data_source.local.alerts_data_source.AlertsDataSource
import com.dmy.weather.data.enums.NotificationType
import com.dmy.weather.data.mapper.getTimeInFullDate
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import com.dmy.weather.platform.notification.NotificationBuilder
import com.dmy.weather.platform.work_manager.AlarmScheduler
import kotlinx.coroutines.flow.Flow

private const val TAG = "AlertRepositoryImpl"

class AlertRepositoryImpl(
    private val alertsDataSource: AlertsDataSource,
    private val weatherRepository: WeatherRepository,
    private val context: Context,
) : AlertRepository {

    override suspend fun updateAlert(alert: AlertEntity) {
        alertsDataSource.updateAlert(alert)
        when (alert.status && alert.notificationType == NotificationType.ALARM) {
            true -> createAlarms(listOf(alert))

            false -> cancelAlert(alert)
        }
    }

    override fun getAlerts(): Flow<List<AlertEntity>> = alertsDataSource.getAlerts()

    override suspend fun getActiveAlerts(): List<AlertEntity> = alertsDataSource.getActiveAlerts()

    private fun cancelAlert(alert: AlertEntity) {
        AlarmScheduler.cancel(context, alert)
        if (!alert.status) {
            NotificationBuilder.showNormalNotification(
                context,
                "Alert Canceled Successfully",
                "${alert.alertType?.name} ${alert.notificationType?.name} is canceled"
            )
        }
    }

    override suspend fun createAlarms(alerts: List<AlertEntity>?): Result<Unit> {
        val activeAlerts = alerts ?: getActiveAlerts()

        return weatherRepository.getWeatherAlerts(activeAlerts)
            .onSuccess { alerts ->
                alerts.forEach { (notificationWeather, alert) ->
                    val triggerTime = notificationWeather.dt * 1000 - (alert.time * 60 * 1000)

                    if (triggerTime <= System.currentTimeMillis()) {
                        Log.w(TAG, "Skipping past alert: ${alert.id}")
                        return@forEach
                    }

                    NotificationBuilder.showNormalNotification(
                        context,
                        "${alert.alertType?.name} Alarm Set Successfully",
                        "Alarm will fire on ${(triggerTime / 1000).getTimeInFullDate()}"
                    )

                    AlarmScheduler.scheduleNotificationAt(
                        context,
                        triggerTime,
                        notificationWeather,
                        alert
                    )
                }
            }
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(it) }
            )
    }

}