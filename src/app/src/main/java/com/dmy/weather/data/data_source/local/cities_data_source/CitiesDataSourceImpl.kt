package com.dmy.weather.data.data_source.local.cities_data_source

import com.dmy.weather.data.db.FavLocationsDao
import com.dmy.weather.data.model.CityModel
import kotlinx.coroutines.flow.Flow

class CitiesDataSourceImpl(
    private val favLocationsDao: FavLocationsDao
) : CitiesDataSource {

    override fun getAllFav(): Flow<List<CityModel>> =
        favLocationsDao.getAll()

    override suspend fun addFav(city: CityModel) =
        favLocationsDao.insert(city)

    override suspend fun removeFav(city: CityModel) =
        favLocationsDao.delete(city)
}