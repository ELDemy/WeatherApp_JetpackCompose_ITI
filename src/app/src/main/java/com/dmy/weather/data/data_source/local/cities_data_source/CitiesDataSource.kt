package com.dmy.weather.data.data_source.local.cities_data_source

import com.dmy.weather.data.model.CityModel
import kotlinx.coroutines.flow.Flow

interface CitiesDataSource {
    fun getAllFav(): Flow<List<CityModel>>
    suspend fun addFav(city: CityModel)
    suspend fun removeFav(city: CityModel)
}