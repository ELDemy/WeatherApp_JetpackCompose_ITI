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
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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

    @Test
    fun getCitiesByName_requestCitiesByEmpty_returnFailure() = runTest {
        //Given
        val cityName = ""
        val citiesList = listOf(fakeDto)
        coEvery { remoteDataSource.getCitiesByName(cityName) } throws Exception()

        //When
        val result = cityRepository.getCitiesByName(cityName)

        //Then
        assertTrue(result.isFailure)
    }

    @Test
    fun getCitiesByName_requestCitiesByName_returnCitiesList() = runTest {
        //Given
        val cityName = "Cairo"
        val citiesList = listOf(fakeDto)
        coEvery { remoteDataSource.getCitiesByName(cityName) } returns citiesList

        //When
        val result = cityRepository.getCitiesByName(cityName)

        //Then
        assertTrue(result.isSuccess)
        val resultList = result.getOrThrow()
        val expectedList = citiesList.map { it.toModel() }
        assertThat(expectedList, `is`(resultList))
    }

    @Test
    fun getGeocodingCityInfoByCity_requestCityInfo_returnCityModel() = runTest {
        //Given
        val cityName = "Cairo"
        coEvery { remoteDataSource.getGeocodingCityByCity(cityName) } returns fakeDto

        //when requesting city Info
        val result = cityRepository.getGeocodingCityInfoByCity(cityName)

        //then the result should be success
        assert(result.isSuccess)

        val expectedModel = fakeDto.toModel()
        val resultModel = result.getOrThrow()
        assertThat(expectedModel, `is`(resultModel))
    }

    @Test
    fun getGeocodingCityInfoByCoord_requestCityInfo_returnCityModel() =
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
    fun getAllFavWithCityName_requestFavCities_returnFlowOfCities() =
        runTest {
            //Given
            val cityName = "Cairo"
            val favCities = mutableListOf<CityModel>()

            coEvery { remoteDataSource.getGeocodingCityByCity(cityName) } returns fakeDto
            coEvery { localDataSource.addFav(any()) } answers { favCities.add(fakeDto.toModel()) }
            coEvery { localDataSource.getAllFav() } returns MutableStateFlow(favCities)

            //when
            val actionResult = cityRepository.addFav(LocationDetails(cityName))
            val result = cityRepository.getAllFav()

            //then
            assertTrue(actionResult.isSuccess)
            assertTrue(result.isSuccess)

            val flowOfCities: Flow<List<CityModel>> = result.getOrThrow()
            val resultList = flowOfCities.first()

            assertThat(favCities, `is`(resultList))
        }

    @Test
    fun getAllFavWithCoord_requestFavCities_returnSuccess() =
        runTest {
            //Given
            val favCities = mutableListOf<CityModel>()

            coEvery { remoteDataSource.getGeocodingCityByCoord(any(), any()) } returns fakeDto
            coEvery { localDataSource.addFav(any()) } answers { favCities.add(fakeDto.toModel()) }
            coEvery { localDataSource.getAllFav() } returns MutableStateFlow(favCities)

            //when
            val actionResult = cityRepository.addFav(LocationDetails(long = "", lat = ""))
            val result = cityRepository.getAllFav()

            //then
            assertTrue(actionResult.isSuccess)
            assertTrue(result.isSuccess)

            val flowOfCities: Flow<List<CityModel>> = result.getOrThrow()
            val resultList = flowOfCities.first()

            assertThat(favCities, `is`(resultList))
        }

    @Test
    fun removeFav_requestRemoveFavCity_returnSuccess() = runTest {
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

    @Test
    fun addFav_requestAddFavCityByName_returnSuccess() = runTest {
        //Given
        val cityName = "Cairo"
        val favCities = mutableListOf<CityModel>()
        coEvery { remoteDataSource.getGeocodingCityByCity(cityName) } returns fakeDto
        coEvery { localDataSource.addFav(any()) } answers {
            favCities.add(fakeDto.toModel())
        }

        //When
        val result = cityRepository.addFav(LocationDetails(cityName))

        //then
        assertTrue(result.isSuccess)
        assertFalse(favCities.isEmpty())
        assertThat(favCities.first(), `is`(fakeDto.toModel()))
    }

    @Test
    fun addFav_requestAddFavCityByCoord_returnSuccess() = runTest {
        //Given
        val favCities = mutableListOf<CityModel>()
        coEvery { remoteDataSource.getGeocodingCityByCoord(any(), any()) } returns fakeDto
        coEvery { localDataSource.addFav(any()) } answers {
            favCities.add(fakeDto.toModel())
        }

        //When
        val result = cityRepository.addFav(LocationDetails(long = "", lat = ""))

        //then
        assertTrue(result.isSuccess)
        assertFalse(favCities.isEmpty())
        assertThat(favCities.first(), `is`(fakeDto.toModel()))
    }

}