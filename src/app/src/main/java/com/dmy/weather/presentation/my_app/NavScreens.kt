package com.dmy.weather.presentation.my_app

import kotlinx.serialization.Serializable

sealed class NavScreens {
    @Serializable
    object HomeScreen : NavScreens()

    @Serializable
    data class WeatherScreen(
        val city: String? = null,
        val long: String? = null,
        val lat: String? = null,
    ) : NavScreens()

    @Serializable
    object SearchScreen : NavScreens()

    @Serializable
    object LocationPickerScreen : NavScreens()

    @Serializable
    object FavoritesScreen : NavScreens()

    @Serializable
    object SettingsScreen : NavScreens()

    @Serializable
    object LanguageSelectionScreen : NavScreens()
}