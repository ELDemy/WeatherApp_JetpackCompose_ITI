package com.dmy.weather.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dmy.weather.data.enums.AlertType
import com.dmy.weather.platform.notification.NotificationType

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey
    val type: String,
    val time: Long,
    val status: Boolean,
    val notification: String,
    val max: Int,
    val min: Int,
) {
    @Ignore
    val alertType: AlertType? = AlertType.getByName(type)

    @Ignore
    val notificationType: NotificationType? = NotificationType.getByName(type)
}
