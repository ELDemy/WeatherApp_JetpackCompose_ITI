package com.dmy.weather.presentation.alerts_screen

sealed class AlertEvent {
    object RequestAlarmPermission : AlertEvent()
}