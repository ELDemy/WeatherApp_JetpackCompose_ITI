package com.dmy.weather.platform.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.platform.notification.NotificationBuilder
import com.dmy.weather.platform.notification.NotificationType
import com.dmy.weather.platform.notification.NotificationType.ALARM
import com.dmy.weather.platform.notification.NotificationType.NOTIFY
import com.dmy.weather.platform.notification.NotificationType.UPDATES

private const val TAG = "WeatherAlarmReceiver"

class WeatherAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationWeather =
            intent.getParcelableExtra<NotificationWeatherModel>(
                "weather",
                NotificationWeatherModel::class.java
            )

        val alert = intent.getStringExtra("notification")
        val notificationType = NotificationType.getByName(alert)
        if (notificationWeather == null) return

        Log.i(TAG, "onReceive: $notificationWeather ")

        when (notificationType) {
            UPDATES, null ->
                NotificationBuilder.showUpdatesNotification(
                    context,
                    notificationWeather,
                )

            NOTIFY ->
                NotificationBuilder.showNotification(
                    context,
                    notificationWeather,

                    )

            ALARM ->
                NotificationBuilder.showAlarmNotification(
                    context,
                    notificationWeather,
                )


        }
    }
}