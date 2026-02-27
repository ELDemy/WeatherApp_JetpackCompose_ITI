@file:OptIn(ExperimentalCoroutinesApi::class)

package com.dmy.weather.presentation.weather_details_screen

import android.util.Log
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WeatherVMTest {
    private lateinit var viewModel: WeatherVM
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var cityRepository: CityRepository
    private lateinit var settingsRepository: SettingsRepository

    private val fakeLocation = LocationDetails(lat = "30.0", long = "31.0")
    private val fakeSettings = UserSettings(
        lang = AppLanguage.DEFAULT,
        unit = UnitSystem.METRIC,
        locationMode = LocationMode.GPS
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0

        weatherRepository = mockk()
        cityRepository = mockk()
        settingsRepository = mockk()

        every { settingsRepository.settingsFlow } returns MutableStateFlow(fakeSettings)

        viewModel = WeatherVM(weatherRepository, cityRepository, settingsRepository)
    }

    @Test
    fun loadWeatherData_whenAllSuccess_updatesAllStatesCorrectly() = runTest {
        // Given
        val fakeWeather = mockk<WeatherModel>()
        val fakeHourly = mockk<HourlyForecastModel>()
        val fakeDaily = mockk<DailyForecastModel>()

        coEvery { weatherRepository.getWeather(fakeLocation) } returns Result.success(fakeWeather)
        coEvery { weatherRepository.getHourlyForecast(fakeLocation) } returns
                Result.success(fakeHourly)
        coEvery { weatherRepository.getDailyForecast(fakeLocation) } returns
                Result.success(fakeDaily)

        // When
        viewModel.loadWeatherData(fakeLocation)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value

        assertFalse(state.currentWeather.isLoading)
        assertEquals(fakeWeather, state.currentWeather.data)
        assertNull(state.currentWeather.error)

        assertFalse(state.hourlyForecast.isLoading)
        assertThat(fakeHourly, `is`(state.hourlyForecast.data))
        assertNull(state.hourlyForecast.error)

        assertFalse(state.dailyForecast.isLoading)
        assertThat(fakeDaily, `is`(state.dailyForecast.data))

        assertNull(state.dailyForecast.error)
    }

    @Test
    fun loadWeatherData_whenCurrentWeatherFails_setsError() = runTest {
        // Given
        coEvery { weatherRepository.getWeather(fakeLocation) } returns Result.failure(Exception("Network Error"))
        coEvery { weatherRepository.getHourlyForecast(fakeLocation) } returns Result.success(mockk())
        coEvery { weatherRepository.getDailyForecast(fakeLocation) } returns Result.success(mockk())

        // When
        viewModel.loadWeatherData(fakeLocation)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value

        assertFalse(state.currentWeather.isLoading)
        assertNull(state.currentWeather.data)
        assertEquals("Network Error", state.currentWeather.error)
    }

    @Test
    fun loadWeatherData_setsIsLoadingTrue_beforeResult() = runTest {
        // Given - suspend indefinitely to catch loading state
        coEvery { weatherRepository.getWeather(fakeLocation) } coAnswers {
            delay(1000)
            Result.success(mockk<WeatherModel>())
        }
        coEvery { weatherRepository.getHourlyForecast(fakeLocation) } coAnswers {
            delay(1000)
            Result.success(mockk())
        }
        coEvery { weatherRepository.getDailyForecast(fakeLocation) } coAnswers {
            delay(1000)
            Result.success(mockk())
        }

        // When
        viewModel.loadWeatherData(fakeLocation)

        // Then - check loading is true before coroutines finish
        assertTrue(viewModel.uiState.value.currentWeather.isLoading)
        assertTrue(viewModel.uiState.value.hourlyForecast.isLoading)
        assertTrue(viewModel.uiState.value.dailyForecast.isLoading)
    }

    @Test
    fun addToFav_whenLocationInitialized_callsRepository() = runTest {
        // Given
        val cityName = "Cairo"
        coEvery { weatherRepository.getWeather(fakeLocation) } returns Result.success(mockk())
        coEvery { weatherRepository.getHourlyForecast(fakeLocation) } returns Result.success(mockk())
        coEvery { weatherRepository.getDailyForecast(fakeLocation) } returns Result.success(mockk())
        coEvery { cityRepository.addFav(any()) } just awaits

        viewModel.loadWeatherData(fakeLocation)
        advanceUntilIdle()

        // When
        viewModel.addToFav(cityName)
        advanceUntilIdle()

        // Then
        coVerify { cityRepository.addFav(fakeLocation.copy(city = cityName)) }
    }


    @Test
    fun settingsChange_whenLocationInitialized_reloadsWeather() = runTest {
        // Given
        val settingsFlow = MutableStateFlow(fakeSettings)
        every { settingsRepository.settingsFlow } returns settingsFlow

        coEvery { weatherRepository.getWeather(fakeLocation) } returns Result.success(mockk())
        coEvery { weatherRepository.getHourlyForecast(fakeLocation) } returns Result.success(mockk())
        coEvery { weatherRepository.getDailyForecast(fakeLocation) } returns Result.success(mockk())

        // Recreate VM with the new flow
        viewModel = WeatherVM(weatherRepository, cityRepository, settingsRepository)
        viewModel.loadWeatherData(fakeLocation)
        advanceUntilIdle()

        // When - settings change
        settingsFlow.value = fakeSettings.copy(unit = UnitSystem.IMPERIAL)
        advanceUntilIdle()

        // Then - weather should be fetched twice (initial + settings change)
        coVerify(exactly = 2) { weatherRepository.getWeather(fakeLocation) }
    }
}