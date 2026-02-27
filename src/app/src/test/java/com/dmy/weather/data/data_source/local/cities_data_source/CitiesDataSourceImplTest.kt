package com.dmy.weather.data.data_source.local.cities_data_source

import com.dmy.weather.data.db.FavLocationsDao
import com.dmy.weather.data.model.CityModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class CitiesDataSourceImplTest {
    private lateinit var dataSource: CitiesDataSourceImpl
    private lateinit var dao: FavLocationsDao

    @Before
    fun setUp() {
        dao = mockk<FavLocationsDao>()
        dataSource = CitiesDataSourceImpl(dao)
    }


    @Test
    fun getAllFav() = runTest {
        //Given
        val fakeCity = CityModel()
        val fakeCities = listOf(fakeCity)
        val fakeCitiesFlow = MutableStateFlow(fakeCities)

        coEvery { dao.getAll() } returns fakeCitiesFlow

        //When
        val citiesFlow = dataSource.getAllFav()

        //Then
        assert(citiesFlow == fakeCitiesFlow)
        val citiesResult = citiesFlow.first()

        assertThat(fakeCities, `is`(citiesResult))
        assertThat(fakeCity, `is`(citiesResult.first()))
    }

    @Test
    fun addFav() = runTest {
        //Given
        val fakeCity = CityModel()
        val fakeCities = mutableListOf<CityModel>()
        val fakeCitiesFlow = MutableStateFlow(fakeCities)

        coEvery { dao.getAll() } returns fakeCitiesFlow
        coEvery { dao.insert(fakeCity) } answers {
            fakeCities.add(fakeCity)
            fakeCitiesFlow.value = fakeCities
        }

        //When
        dataSource.addFav(fakeCity)
        val citiesFlow = dataSource.getAllFav()

        //Then
        assert(citiesFlow == fakeCitiesFlow)
        val citiesResult = citiesFlow.first()

        assertTrue(citiesResult.contains(fakeCity))
        assertThat(fakeCities, `is`(citiesResult))
        assertThat(fakeCity, `is`(citiesResult.first()))
    }

    @Test
    fun removeFav() = runTest {
        //Given
        val fakeCity = CityModel()
        val fakeCities = mutableListOf(fakeCity)
        val fakeCitiesFlow = MutableStateFlow(fakeCities)

        coEvery { dao.getAll() } returns fakeCitiesFlow
        coEvery { dao.delete(fakeCity) } answers {
            fakeCities.remove(fakeCity)
        }

        //When
        dataSource.removeFav(fakeCity)
        val citiesFlow = dataSource.getAllFav()

        //Then
        val citiesResult = citiesFlow.first()
        assert(citiesResult.isEmpty())
    }

}