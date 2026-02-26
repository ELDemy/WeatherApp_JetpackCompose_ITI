package com.dmy.weather.data.repo.city_repo

import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.LocationDetails
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun getGeocodingCityInfoByCity(city: String): Result<CityModel?>

    suspend fun getCitiesByName(city: String): Result<List<CityModel>>

    suspend fun getGeocodingCityInfoByCoord(long: String, lat: String): Result<CityModel?>

    fun getAllFav(): Result<Flow<List<CityModel>>>

    suspend fun addFav(locationDetails: LocationDetails): Result<Unit>

    suspend fun removeFav(city: CityModel): Result<Unit>

}