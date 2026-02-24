package com.dmy.weather.presentation.permissions.notification

sealed class PermissionResult {
    object Granted : PermissionResult()
    object Denied : PermissionResult()
    object PermanentlyDenied : PermissionResult()
}