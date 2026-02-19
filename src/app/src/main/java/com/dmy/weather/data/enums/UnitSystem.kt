package com.dmy.weather.data.enums

enum class UnitSystem(val display: String, val info: String) {
    STANDARD("standard", "Kelvin (K) • Meter/sec (m/s)"),
    METRIC("metric", "Celsius (°C) • Meter/sec (m/s)"),
    IMPERIAL("Imperial", "Fahrenheit (°F) • Miles/hour (mph)");

    companion object {
        val DEFAULT = METRIC

        fun fromName(name: String?): UnitSystem {
            return UnitSystem.entries.find { it.name == name } ?: DEFAULT
        }
    }
}