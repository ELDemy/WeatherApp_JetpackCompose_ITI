package com.dmy.weather.platform.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.dmy.weather.MainActivity
import com.dmy.weather.R
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.platform.broadcast.AlarmDismissReceiver

object NotificationBuilder {
    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannels(NotificationType.entries.map { it.notificationChannel() })
        }
    }

    fun showUpdatesNotification(
        context: Context,
        weather: NotificationWeatherModel,
    ) {
        val notification = baseBuilder(context, NotificationType.UPDATES, weather)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .build()

        notify(context, notification)
    }

    fun showNotification(
        context: Context,
        weather: NotificationWeatherModel,
    ) {
        val notification = baseBuilder(context, NotificationType.Notify, weather)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .build()

        notify(context, notification)
    }

    fun showAlarmNotification(
        context: Context,
        weather: NotificationWeatherModel,
    ) {
        val notificationId = System.currentTimeMillis().toInt()

        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            Intent(context, AlarmDismissReceiver::class.java).apply {
                action = AlarmDismissReceiver.ACTION_DISMISS
                putExtra(AlarmDismissReceiver.EXTRA_NOTIFICATION_ID, notificationId)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            baseBuilder(context, NotificationType.ALARM, weather)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(false)
                .setOngoing(true)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    context.getString(R.string.I_will_take_care),
                    dismissPendingIntent
                )
                .build().also {
                    it.flags = it.flags or Notification.FLAG_INSISTENT
                }

        notify(context, notification, notificationId)
    }

    private fun notify(
        context: Context,
        notification: Notification,
        id: Int = System.currentTimeMillis().toInt()
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
    }


    private fun buildRemoteViews(
        context: Context,
        weather: NotificationWeatherModel,
    ): Pair<RemoteViews, RemoteViews> {
        val packageName = context.packageName


        val expanded = RemoteViews(packageName, R.layout.custom_notification_expanded).apply {
            setTextViewText(R.id.notif_city, "${weather.cityName}, ${weather.country}")
            setTextViewText(R.id.notif_temp, "${weather.temperature}°")
            setTextViewText(R.id.notif_desc, weather.description)
            weather.icon?.let { setImageViewResource(R.id.notif_icon, it) }
            weather.bg?.let { setImageViewResource(R.id.notif_bg, it) }
            when (weather.max) {
                null -> setViewVisibility(R.id.notif_high, View.GONE)
                else -> setTextViewText(R.id.notif_high, weather.max.toString())
            }
            when (weather.min) {
                null -> setViewVisibility(R.id.notif_low, View.GONE)
                else -> setTextViewText(R.id.notif_low, weather.min.toString())
            }
        }

        val collapsed = RemoteViews(packageName, R.layout.custom_notification).apply {
            setTextViewText(R.id.notif_city, "${weather.cityName}, ${weather.country}")
            setTextViewText(R.id.notif_temp, "${weather.temperature}°")
            setTextViewText(R.id.notif_desc, weather.description)
            weather.icon?.let { setImageViewResource(R.id.notif_icon, it) }
            weather.bg?.let { setImageViewResource(R.id.notif_bg, it) }
        }

        return Pair(collapsed, expanded)
    }

    private fun baseBuilder(
        context: Context,
        type: NotificationType,
        weather: NotificationWeatherModel,
    ): NotificationCompat.Builder {
        val (collapsed, expanded) = buildRemoteViews(context, weather)
        return NotificationCompat.Builder(context, type.id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(collapsed)
            .setCustomBigContentView(expanded)
            .setAutoCancel(true)
    }
}