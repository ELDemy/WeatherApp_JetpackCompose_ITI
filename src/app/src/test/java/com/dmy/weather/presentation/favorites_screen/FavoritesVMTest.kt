@file:OptIn(ExperimentalCoroutinesApi::class)

package com.dmy.weather.presentation.favorites_screen

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.city_repo.CityRepositoryImpl
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FavoritesVMTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var viewModel: FavoritesVM
    lateinit var cityRepository: CityRepository
    lateinit var weatherRepository: WeatherRepository


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0

        weatherRepository = mockk<WeatherRepositoryImpl>()
        cityRepository = mockk<CityRepositoryImpl>()
        viewModel = FavoritesVM(cityRepository, weatherRepository)
    }

    @Test
    fun loadFavoriteCities_WhenSuccess_returnsCorrectData() = runTest {
        //Given
        val fakeCity = CityModel()
        val fakeCities = listOf(fakeCity)
        val fakeCitiesFlow = MutableStateFlow(fakeCities)

        coEvery { cityRepository.getAllFav() } returns Result.success(fakeCitiesFlow)

        //When
        viewModel.loadFavoriteCities()
        advanceUntilIdle()

        //Then
        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertNotNull(state.data)
        assert(state.data.contains(fakeCity))

        assertNull(state.error)
    }

    @Test
    fun loadFavoriteCities_WhenFailure_returnsError() = runTest {
        //Given
        coEvery { cityRepository.getAllFav() } returns Result.failure(Exception())

        //When
        viewModel.loadFavoriteCities()
        advanceUntilIdle()

        //Then
        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertNotNull(state.error)
        assertNull(state.data)
    }

    @Test
    fun getWeather_WhenSuccess_returnsCorrectData() = runTest {
        //Given
        val weatherModel = mockk<WeatherModel>(relaxed = true)
        coEvery { weatherRepository.getWeather(any()) } returns Result.success(weatherModel)

        //When
        viewModel.getWeather(long = "", lat = "") {
            //Then
            assertNotNull(it)
        }
    }

    @Test
    fun getWeather_WhenFa_returnsError() = runTest {
        //Given
        coEvery { weatherRepository.getWeather(any()) } returns Result.failure(Exception())

        //When
        viewModel.getWeather(long = "", lat = "") {

            //Then
            assertNull(it)
        }
    }


}