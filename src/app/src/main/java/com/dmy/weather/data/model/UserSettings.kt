package com.dmy.weather.data.model

import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem

data class UserSettings(
    val lang: AppLanguage? = null,
    val unit: UnitSystem? = null,
    val locationMode: LocationMode? = null
)