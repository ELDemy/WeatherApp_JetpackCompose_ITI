package com.dmy.weather.data.data_source.remote.geocoding_data_source

import com.dmy.weather.data.dto.GeocodingCityDTO

interface GeocodingRemoteDataSource {
    suspend fun getGeocodingCityByCity(city: String): GeocodingCityDTO?

    suspend fun getCitiesByName(city: String): List<GeocodingCityDTO>?

    suspend fun getGeocodingCityByCoord(long: String, lat: String): GeocodingCityDTO?
}