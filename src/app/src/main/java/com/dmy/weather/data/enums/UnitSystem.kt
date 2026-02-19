package com.dmy.weather.data.enums

enum class UnitSystem(val display: String, val info: String) {
    STANDARD("standard", "Kelvin (K) • Meter/sec (m/s)"),
    METRIC("metric", "Celsius (°C) • Meter/sec (m/s)"),
    IMPERIAL("Imperial", "Fahrenheit (°F) • Miles/hour (mph)")
}