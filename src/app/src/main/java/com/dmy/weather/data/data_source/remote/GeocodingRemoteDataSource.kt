package com.dmy.weather.data.data_source.remote

import com.dmy.weather.data.dto.GeocodingCityDTO
import com.dmy.weather.data.network.GeocodingService

class GeocodingRemoteDataSource(val geocodingService: GeocodingService) {
    companion object {
        private const val TAG = "GeocodingRemoteDataSour"
    }

    suspend fun getGeocodingCityByCity(city: String): GeocodingCityDTO? {
        return geocodingService.getGeocodingCityByCity(city)?.firstOrNull()
    }

    suspend fun getGeocodingCityByCoord(long: String, lat: String): GeocodingCityDTO? {
        return geocodingService.getGeocodingCityByCoord(long, lat)?.firstOrNull()
    }
}
