package com.dmy.weather.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.presentation.notification.NotificationBuilder

private const val TAG = "WeatherAlarmReceiver"

class WeatherAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationWeather =
            intent.getParcelableExtra<NotificationWeatherModel>(
                "weather",
                NotificationWeatherModel::class.java
            )
        Log.i(TAG, "onReceive: $notificationWeather ")
//        NotificationBuilder.testAlarmNotification(context, weather, null)
        notificationWeather?.let {
            NotificationBuilder.showAlarmNotification(context, notificationWeather, null)
        }
    }
}