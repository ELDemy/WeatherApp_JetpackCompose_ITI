package com.dmy.weather.platform.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmDismissReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_DISMISS) {
            val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
            if (notificationId != -1) {
                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(notificationId)
            }
        }
    }

    companion object {
        const val ACTION_DISMISS = "com.dmy.weather.ACTION_DISMISS_ALARM"
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
    }
}