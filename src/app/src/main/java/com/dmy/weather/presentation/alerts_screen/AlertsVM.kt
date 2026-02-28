package com.dmy.weather.presentation.alerts_screen

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.AlertType
import com.dmy.weather.data.enums.NotificationType
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.data.repo.alert_repo.AlertRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlertsVM(
    private val alertRepository: AlertRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _events = MutableSharedFlow<AlertEvent>()
    val events = _events.asSharedFlow()

    val alerts: StateFlow<List<AlertEntity>> = alertRepository
        .getAlerts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    var unit by mutableStateOf(UnitSystem.METRIC)
        private set

    init {
        viewModelScope.launch {
            unit = settingsRepository.getUnit()
        }
    }


    fun toggleAlert(type: AlertType, enabled: Boolean) {
        viewModelScope.launch {
            val existing = alerts.value.find { it.alertType == type }
            val updated = existing?.copy(status = enabled) ?: AlertEntity(
                type = type.name,
                time = 15,
                status = enabled,
                notification = NotificationType.NOTIFY.name,
                max = defaultMax(type),
                min = defaultMin(type)
            )
            alertRepository.updateAlert(updated)
        }
    }

    fun updateMinutesBefore(type: AlertType, minutes: Int) {
        viewModelScope.launch {
            val existing = alerts.value.find { it.alertType == type } ?: return@launch
            alertRepository.updateAlert(existing.copy(time = minutes.toLong()))
        }
    }

    fun updateNotificationType(
        type: AlertType,
        notificationType: NotificationType,
        context: Context
    ) {
        viewModelScope.launch {
            val existing = alerts.value.find { it.alertType == type } ?: return@launch
            alertRepository.updateAlert(existing.copy(notification = notificationType.name))

            if (notificationType == NotificationType.ALARM) {
                checkAlarmPermission(context)
            }
        }


    }

    fun updateRange(type: AlertType, min: Int, max: Int) {
        viewModelScope.launch {
            val existing = alerts.value.find { it.alertType == type } ?: return@launch
            alertRepository.updateAlert(existing.copy(min = min, max = max))
        }
    }

    private fun defaultMin(type: AlertType) = when (type) {
        AlertType.TEMP -> -10
        AlertType.HUMIDITY -> 20
        AlertType.PRESSURE -> 980
        else -> 0
    }

    private fun defaultMax(type: AlertType) = when (type) {
        AlertType.TEMP -> 35
        AlertType.HUMIDITY -> 80
        AlertType.PRESSURE -> 1050
        else -> 0
    }

    private suspend fun checkAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                _events.emit(AlertEvent.RequestAlarmPermission)
            }
        }
    }
}

