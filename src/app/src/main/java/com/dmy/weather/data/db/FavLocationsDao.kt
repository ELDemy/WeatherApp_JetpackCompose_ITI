package com.dmy.weather.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dmy.weather.data.model.CityModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavLocationsDao {
    @Query("Select * from favLocations")
    fun getAll(): Flow<List<CityModel>>

    @Upsert
    suspend fun insert(city: CityModel)

    @Delete
    suspend fun delete(city: CityModel)

}