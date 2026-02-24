package com.dmy.weather.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.notification.NotificationBuilder

class WeatherAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val weather = intent.getParcelableExtra<WeatherModel>("weather", WeatherModel::class.java)
        
        weather?.let {
            NotificationBuilder.showAlarmNotification(context, weather, null)

        }
    }
}