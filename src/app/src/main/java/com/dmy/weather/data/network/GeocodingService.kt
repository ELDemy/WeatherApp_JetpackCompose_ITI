package com.dmy.weather.data.network

import com.dmy.weather.data.dto.GeocodingCityDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("geo/1.0/direct")
    suspend fun getGeocodingCityByCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
    ): List<GeocodingCityDTO>?

    @GET("geo/1.0/direct")
    suspend fun getGeocodingCityByCoord(
        @Query("lon") lon: String,
        @Query("lat") lat: String,
        @Query("limit") limit: Int = 1,
    ): List<GeocodingCityDTO>?
}