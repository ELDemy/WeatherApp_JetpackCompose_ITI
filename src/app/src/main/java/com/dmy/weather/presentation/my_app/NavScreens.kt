package com.dmy.weather.presentation.my_app

import kotlinx.serialization.Serializable

sealed class NavScreens {
    @Serializable
    object HomeScreen : NavScreens()
}