package com.dmy.weather.data.enums


enum class AlertType(val desc: String? = null) {
    TEMP,
    HUMIDITY,
    PRESSURE,
    CLOUDS("broken clouds"),
    SNOW("snow"),
    RAIN("rain");

    companion object {
        fun getByName(name: String): AlertType? =
            entries.find { it.name == name.uppercase() }
    }
}
