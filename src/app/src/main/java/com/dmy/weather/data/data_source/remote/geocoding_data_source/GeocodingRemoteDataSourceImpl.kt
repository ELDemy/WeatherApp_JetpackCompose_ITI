package com.dmy.weather.data.data_source.remote.geocoding_data_source

import com.dmy.weather.data.dto.GeocodingCityDTO
import com.dmy.weather.data.network.GeocodingService

class GeocodingRemoteDataSourceImpl(
    val geocodingService: GeocodingService
) : GeocodingRemoteDataSource {
    companion object {
        private const val TAG = "GeocodingRemoteDataSour"
    }

    override suspend fun getGeocodingCityByCity(city: String): GeocodingCityDTO? {
        return geocodingService.getGeocodingCityByCity(city)?.firstOrNull()
    }

    override suspend fun getCitiesByName(city: String): List<GeocodingCityDTO>? {
        return geocodingService.getGeocodingCityByCity(city, limit = 0)
    }

    override suspend fun getGeocodingCityByCoord(long: String, lat: String): GeocodingCityDTO? {
        return geocodingService.getGeocodingCityByCoord(long, lat)?.firstOrNull()
    }
}