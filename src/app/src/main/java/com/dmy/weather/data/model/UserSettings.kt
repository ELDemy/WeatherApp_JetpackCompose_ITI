package com.dmy.weather.data.model

import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem

data class UserSettings(
    val lang: String? = null,
    val unit: UnitSystem? = null,
    val locationMode: LocationMode? = null
)