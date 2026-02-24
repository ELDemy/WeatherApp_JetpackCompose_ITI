package com.dmy.weather.data.repo

import android.util.Log
import com.dmy.weather.data.data_source.remote.GeocodingRemoteDataSource
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.utils.exceptions.NullDataException
import com.dmy.weather.utils.mapFailure

private const val TAG = "WeatherRepo"

class LocationRepository(
    val geocodingRemoteDataSource: GeocodingRemoteDataSource
) {
    suspend fun getGeocodingCityInfoByCity(city: String): Result<CityModel> {
        return runCatching {
            val geocodingCityDTO =
                geocodingRemoteDataSource.getGeocodingCityByCity(city) ?: throw NullDataException()
            val cityModel = geocodingCityDTO.toModel()

            Log.i(TAG, "geocodingCityDTO: $geocodingCityDTO")
            Log.i(TAG, "cityModel: $cityModel")

            return Result.success(cityModel)
        }.mapFailure()
    }

    suspend fun getCitiesByName(city: String): Result<List<CityModel>> {
        return runCatching {
            val geocodingCitiesDTOs =
                geocodingRemoteDataSource.getCitiesByName(city) ?: throw NullDataException()
            val cityModels = geocodingCitiesDTOs.map { it.toModel() }

            Log.i(TAG, "geocodingCityDTO: $geocodingCitiesDTOs")
            Log.i(TAG, "cityModel: $cityModels")

            return Result.success(cityModels)
        }.mapFailure()
    }

    suspend fun getGeocodingCityInfoByCoord(long: String, lat: String): CityModel? {
        val geocodingCityDTO = geocodingRemoteDataSource.getGeocodingCityByCoord(long, lat)
        val cityModel = geocodingCityDTO?.toModel()

        Log.i(TAG, "geocodingCityDTO: $geocodingCityDTO")
        Log.i(TAG, "cityModel: $cityModel")

        return cityModel
    }
}