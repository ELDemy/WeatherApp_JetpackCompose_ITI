package com.dmy.weather.data.enums

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat

enum class NotificationType(
    val id: String,
    val named: String,
    val description: String,
    val importance: Int
) {
    UPDATES(
        "weather_updates",
        "Daily app_name Updates",
        "Channel for regular rain or sun updates",
        NotificationManager.IMPORTANCE_DEFAULT
    ),
    NOTIFY(
        "weather_notification",
        "app_name Notifications",
        "Channel for weather conditions",
        NotificationManager.IMPORTANCE_HIGH
    ),
    ALARM(
        "weather_alarm",
        "Urgent app_name Alerts",
        "Channel for life-threatening weather conditions",
        NotificationManager.IMPORTANCE_MAX
    );

    companion object {
        fun getByName(name: String?): NotificationType? =
            entries.find { it.name.equals(name, ignoreCase = true) }
    }

    fun notificationChannel(): NotificationChannel =
        NotificationChannel(id, named, importance)
            .apply {
                description = this@NotificationType.description
                enableLights(true)
                lightColor = Color.RED
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

                when (this@NotificationType) {
                    ALARM -> {
                        setBypassDnd(true)
                        enableVibration(true)
                        vibrationPattern = longArrayOf(0, 500, 250, 500, 250, 500)
                        val audioAttributes = AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                        setSound(
                            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),
                            audioAttributes
                        )
                    }

                    NOTIFY -> {
                        enableVibration(true)
                        vibrationPattern = longArrayOf(0, 80)
                    }

                    UPDATES -> {
                        enableVibration(false)
                    }
                }
            }
}