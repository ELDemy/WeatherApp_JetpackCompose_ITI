package com.dmy.weather.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dmy.weather.data.model.CityModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavLocationsDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var dao: FavLocationsDao
    lateinit var db: AppDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        dao = db.favLocationsDao()
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun getAll_whenEmpty_returnsEmptyList() = runTest {
        val result = dao.getAll().first()
        assert(result.isEmpty())
    }

    @Test
    fun insertAndGetData_returnsCorrectData() = runTest {
        //Given
        val city = CityModel()

        //When
        dao.insert(city)
        val favCities = dao.getAll().first()

        //Then
        assert(favCities.contains(city))

    }

    @Test
    fun insertMultiple_returnsAllCities() = runTest {
        //Given
        val city1 = CityModel(longitude = 1.0)
        val city2 = CityModel(longitude = 2.0)

        //When
        dao.insert(city1)
        dao.insert(city2)
        val result = dao.getAll().first()

        //Then
        assert(result.size == 2)
        assert(result.containsAll(listOf(city1, city2)))
    }

    @Test
    fun insertAndDeleteData_returnsCorrectData() = runTest {
        //Given
        val city = CityModel()

        //When
        dao.insert(city)
        dao.delete(city)
        val favCities = dao.getAll().first()

        //Then
        assert(!favCities.contains(city))
    }

    @Test
    fun deleteNonExistent_doesNotCrash() = runTest {
        //Given
        val city = CityModel()

        //When
        dao.delete(city)
        val result = dao.getAll().first()

        //Then
        assert(result.isEmpty())
    }
}