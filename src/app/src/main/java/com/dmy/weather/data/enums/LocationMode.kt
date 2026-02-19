package com.dmy.weather.data.enums

enum class LocationMode {
    GPS, MAP;

    companion object {
        val DEFAULT = GPS

        fun fromName(name: String?): LocationMode {
            return LocationMode.entries.find { it.name == name } ?: DEFAULT
        }
    }
}