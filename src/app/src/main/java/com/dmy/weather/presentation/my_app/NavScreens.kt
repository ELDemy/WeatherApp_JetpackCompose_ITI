package com.dmy.weather.presentation.my_app

import kotlinx.serialization.Serializable

sealed class NavScreens {
    @Serializable
    object HomeScreen : NavScreens()

    @Serializable
    object SearchScreen : NavScreens()

    @Serializable
    object FavoritesScreen : NavScreens()

    @Serializable
    object SettingsScreen : NavScreens()

    @Serializable
    object LanguageSelectionScreen : NavScreens()
}