package com.dmy.weather.data.data_source.remote

import com.dmy.weather.data.dto.GeocodingCityDTO
import com.dmy.weather.data.network.GeocodingService
import com.dmy.weather.data.network.WeatherNetwork

class GeocodingRemoteDataSource {
    companion object {
        private const val TAG = "GeocodingRemoteDataSour"
        private val geocodingService: GeocodingService = WeatherNetwork.geocodingService
    }

    suspend fun getGeocodingCityByCity(city: String): GeocodingCityDTO? {
        return geocodingService.getGeocodingCityByCity(city)?.firstOrNull()
    }

    suspend fun getGeocodingCityByCoord(long: String, lat: String): GeocodingCityDTO? {
        return geocodingService.getGeocodingCityByCoord(long, lat)?.firstOrNull()
    }
}
