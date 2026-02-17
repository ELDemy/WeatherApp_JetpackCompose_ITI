package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.GeocodingCityDTO
import com.dmy.weather.data.model.CityModel

fun GeocodingCityDTO.toModel(): CityModel {
    val localizedName = getLocalName(localNames)

    return CityModel(
        name = name,
        localName = localizedName ?: name,
        latitude = lat,
        longitude = lon,
        country = country,
        state = state ?: ""
    )
}

private fun getLocalName(localNames: Map<String, String>?): String? {
    return localNames?.run {
        return get("en") ?: get("ar")
    }
}