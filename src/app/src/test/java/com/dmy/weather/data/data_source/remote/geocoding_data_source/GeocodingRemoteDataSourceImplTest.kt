package com.dmy.weather.data.data_source.remote.geocoding_data_source

import android.util.Log
import com.dmy.weather.data.dto.GeocodingCityDTO
import com.dmy.weather.data.network.GeocodingService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNull

class GeocodingRemoteDataSourceImplTest {
    lateinit var remoteDataSource: GeocodingRemoteDataSourceImpl
    lateinit var service: GeocodingService

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0

        service = mockk<GeocodingService>()
        remoteDataSource = GeocodingRemoteDataSourceImpl(service)
    }

    @Test
    fun getGeocodingCityByCity_requestWithCityName_returnCityDto() =
        runTest {
            //Given
            val cityName = "Cairo"
            val fakeDto = GeocodingCityDTO()
            val citiesList = mutableListOf(fakeDto)

            coEvery { service.getGeocodingCityByCity(cityName) } returns citiesList

            //When
            val city = remoteDataSource.getGeocodingCityByCity(cityName)

            //Then
            assertThat(fakeDto, `is`(city))
        }

    @Test
    fun getGeocodingCityByCity_requestWithEmptyCity_returnFailure() =
        runTest {
            //Given
            val cityName = ""
            coEvery { service.getGeocodingCityByCity(cityName) } returns null

            //When
            val city = remoteDataSource.getGeocodingCityByCity(cityName)

            //Then
            assertNull(city)
        }

    @Test
    fun getCitiesByName_requestWithCityName_returnListOfCities() =
        runTest {
            //Given
            val cityName = "Cairo"
            val fakeDto = GeocodingCityDTO()
            val citiesList = mutableListOf(fakeDto)

            coEvery { service.getGeocodingCityByCity(cityName, limit = 0) } returns citiesList

            //When
            val citiesResult = remoteDataSource.getCitiesByName(cityName)

            //Then
            assertThat(citiesList, `is`(citiesResult))
        }

    @Test
    fun getCitiesByName_requestWithEmptyCityName_returnNull() =
        runTest {
            //Given
            val cityName = ""
            coEvery { service.getGeocodingCityByCity(cityName, limit = 0) } returns null

            //When
            val city = remoteDataSource.getCitiesByName(cityName)

            //Then
            assertNull(city)
        }

    @Test
    fun getGeocodingCityByCoord_requestWithCoord_returnCityDto() = runTest {
        //Given
        val fakeDto = GeocodingCityDTO()
        val citiesList = mutableListOf(fakeDto)

        coEvery { service.getGeocodingCityByCoord(any(), any()) } returns citiesList

        //When
        val city = remoteDataSource.getGeocodingCityByCoord("", "")

        //Then
        assertThat(city, `is`(fakeDto))
    }
}