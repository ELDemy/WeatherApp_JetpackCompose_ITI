package com.dmy.weather.data.data_source.local

import com.dmy.weather.data.db.FavLocationsDao
import com.dmy.weather.data.model.CityModel
import kotlinx.coroutines.flow.Flow

class CitiesDataSource(private val favLocationsDao: FavLocationsDao) {

    fun getAllFav(): Flow<List<CityModel>> =
        favLocationsDao.getAll()

    suspend fun addFav(city: CityModel) =
        favLocationsDao.insert(city)

    suspend fun removeFav(city: CityModel) =
        favLocationsDao.delete(city)
}