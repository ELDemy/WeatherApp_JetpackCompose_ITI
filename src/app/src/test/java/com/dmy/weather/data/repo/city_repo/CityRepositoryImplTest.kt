package com.dmy.weather.data.repo.city_repo

import android.util.Log
import com.dmy.weather.data.data_source.local.cities_data_source.CitiesDataSource
import com.dmy.weather.data.data_source.remote.geocoding_data_source.GeocodingRemoteDataSource
import com.dmy.weather.data.dto.GeocodingCityDTO
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.LocationDetails
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class CityRepositoryImplTest {
    lateinit var cityRepository: CityRepository
    lateinit var remoteDataSource: GeocodingRemoteDataSource
    lateinit var localDataSource: CitiesDataSource

    lateinit var fakeDto: GeocodingCityDTO

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0

        remoteDataSource = mockk<GeocodingRemoteDataSource>()
        localDataSource = mockk<CitiesDataSource>()

        cityRepository = CityRepositoryImpl(
            remoteDataSource,
            localDataSource
        )

        fakeDto = GeocodingCityDTO()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getGeocodingCityInfoByCity_requestCityInfoFromCitiesDataSource_returnCityModel() = runTest {
        //Given
        coEvery { remoteDataSource.getGeocodingCityByCity(any()) } returns fakeDto

        //when requesting city Info
        val result = cityRepository.getGeocodingCityInfoByCity("Cairo")
        //then the result should be success
        assert(result.isSuccess)

        val expectedModel = fakeDto.toModel()
        val resultModel = result.getOrThrow()
        assertThat(expectedModel, `is`(resultModel))
    }

    @Test
    fun getGeocodingCityInfoByCoord_requestCityInfoFromCitiesDataSource_returnCityModel() =
        runTest {
            //Given
            coEvery { remoteDataSource.getGeocodingCityByCoord(any(), any()) } returns fakeDto

            //When
            val result = cityRepository.getGeocodingCityInfoByCoord("", "")

            //then
            assertTrue(result.isSuccess)

            val expectedModel = fakeDto.toModel()
            val resultModel = result.getOrThrow()
            assertThat(expectedModel, `is`(resultModel))
        }

    @Test
    fun getAllFav_requestFavCitiesFromLocalCitiesDataSource_returnFlowOfCities() = runTest {
        //Given
        val favCities = mutableListOf<CityModel>()

        coEvery { remoteDataSource.getGeocodingCityByCity(any()) } returns fakeDto
        coEvery { localDataSource.addFav(any()) } answers { favCities.add(fakeDto.toModel()) }
        coEvery { localDataSource.getAllFav() } returns MutableStateFlow(favCities)

        //when
        cityRepository.addFav(LocationDetails("Cairo"))
        val result = cityRepository.getAllFav()

        //then
        assertTrue(result.isSuccess)

        val flowOfCities: Flow<List<CityModel>> = result.getOrThrow()
        val resultList = flowOfCities.first()

        assertThat(favCities, `is`(resultList))
    }

    @Test
    fun removeFav_requestRemoveFavCityFromLocalCitiesDataSource_returnSuccess() = runTest {
        //Given
        val favCities = mutableListOf<CityModel>(fakeDto.toModel())

        coEvery { localDataSource.removeFav(any()) } answers {
            favCities.remove(fakeDto.toModel())
        }

        //When
        val result = cityRepository.removeFav(fakeDto.toModel())

        //then
        assertTrue(result.isSuccess)
        assertTrue(favCities.isEmpty())
    }

}