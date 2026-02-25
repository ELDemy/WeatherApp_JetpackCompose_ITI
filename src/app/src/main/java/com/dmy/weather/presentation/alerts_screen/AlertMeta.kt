package com.dmy.weather.presentation.alerts_screen

data class AlertMeta(
    val icon: String,
    val label: String,
    val unit: String,
    val minRange: Float,
    val maxRange: Float
)