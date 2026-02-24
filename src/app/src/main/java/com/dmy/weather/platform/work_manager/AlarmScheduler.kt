package com.dmy.weather.platform.work_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.platform.broadcast.WeatherAlarmReceiver

object AlarmScheduler {

    fun scheduleNotificationAt(
        context: Context,
        triggerAtMillis: Long,
        notificationWeather: NotificationWeatherModel
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(it)
                }
                return
            }
        }
        val intent = Intent(context, WeatherAlarmReceiver::class.java).apply {
            putExtra("weather", notificationWeather)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    fun cancel(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, WeatherAlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}