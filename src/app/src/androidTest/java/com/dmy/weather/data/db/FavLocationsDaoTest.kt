package com.dmy.weather.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dmy.weather.data.model.CityModel
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.delay
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
        val city = CityModel(name = "city", country = "Country", longitude = 1.0, latitude = 1.0)
        val city2 = CityModel(name = "city2", country = "Country2", longitude = 2.0, latitude = 2.0)
        val city3 = CityModel(name = "city3", country = "Country3", longitude = 3.0, latitude = 3.0)
        val city4 = CityModel(name = "city4", country = "Country4", longitude = 4.0, latitude = 4.0)

        //When
        dao.insert(city)
        dao.insert(city2)
        dao.insert(city3)
        dao.insert(city4)
        delay(1000)
        dao.delete(city)
        delay(5000)
        val favCities = dao.getAll().first()

        //Then
        assertFalse(favCities.contains(city))
        assert(favCities.size == 3)

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