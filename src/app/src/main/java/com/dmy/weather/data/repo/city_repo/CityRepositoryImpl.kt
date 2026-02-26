package com.dmy.weather.data.repo.city_repo

import android.util.Log
import com.dmy.weather.data.data_source.local.cities_data_source.CitiesDataSource
import com.dmy.weather.data.data_source.remote.geocoding_data_source.GeocodingRemoteDataSource
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.utils.exceptions.NullDataException
import com.dmy.weather.utils.mapFailure
import kotlinx.coroutines.flow.Flow

private const val TAG = "WeatherRepo"

class CityRepositoryImpl(
    private val geocodingRemoteDataSource: GeocodingRemoteDataSource,
    private val citiesDataSource: CitiesDataSource,
) : CityRepository {
    override suspend fun getGeocodingCityInfoByCity(city: String): Result<CityModel?> {
        Log.i(TAG, "getGeocodingCityInfoByCity: ")
        return runCatching {
            val geocodingCityDTO =
                geocodingRemoteDataSource.getGeocodingCityByCity(city) ?: throw NullDataException()
            val cityModel = geocodingCityDTO.toModel()

            Log.i(TAG, "geocodingCityDTO: $geocodingCityDTO")
            Log.i(TAG, "cityModel: $cityModel")

            return Result.success(cityModel)
        }.mapFailure()
    }

    override suspend fun getCitiesByName(city: String): Result<List<CityModel>> {
        return runCatching {
            val geocodingCitiesDTOs =
                geocodingRemoteDataSource.getCitiesByName(city) ?: throw NullDataException()
            val cityModels = geocodingCitiesDTOs.map { it.toModel() }

            Log.i(TAG, "geocodingCityDTO: $geocodingCitiesDTOs")
            Log.i(TAG, "cityModel: $cityModels")

            return Result.success(cityModels)
        }.mapFailure()
    }

    override suspend fun getGeocodingCityInfoByCoord(
        long: String,
        lat: String
    ): Result<CityModel?> {
        Log.i(TAG, "getGeocodingCityInfoByCoord: long:$long , lat:$lat")
        return runCatching {
            val geocodingCityDTO = geocodingRemoteDataSource.getGeocodingCityByCoord(long, lat)
                ?: throw NullDataException()

            val cityModel = geocodingCityDTO.toModel()

            Log.i(TAG, "geocodingCityDTO: $geocodingCityDTO")
            Log.i(TAG, "cityModel: $cityModel")

            cityModel
        }.mapFailure()
    }

    override fun getAllFav(): Result<Flow<List<CityModel>>> {
        return runCatching {
            citiesDataSource.getAllFav()
        }.mapFailure()
    }

    override suspend fun addFav(locationDetails: LocationDetails): Result<Unit> {
        return runCatching {
            val city: CityModel = when {
                locationDetails.city != null -> {
                    getGeocodingCityInfoByCity(locationDetails.city).fold(
                        onSuccess = { it },
                        onFailure = { null }
                    )
                }

                locationDetails.lat != null && locationDetails.long != null -> {
                    getGeocodingCityInfoByCoord(
                        locationDetails.long,
                        locationDetails.lat
                    ).fold(
                        onSuccess = { it },
                        onFailure = { null }
                    )
                }

                else -> null
            } ?: throw NullDataException()

            citiesDataSource.addFav(city)

            return Result.success(Unit)
        }.mapFailure()
    }

    override suspend fun removeFav(city: CityModel): Result<Unit> {
        return runCatching {
            citiesDataSource.removeFav(city)
        }.mapFailure()
    }
}