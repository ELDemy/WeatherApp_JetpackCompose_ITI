@file:OptIn(ExperimentalCoroutinesApi::class)

package com.dmy.weather.data.repo.weather_repo

import android.util.Log
import com.dmy.weather.data.data_source.remote.weather_data_source.WeatherRemoteDataSource
import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastItem
import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.mapper.filterBasedOnAlerts
import com.dmy.weather.data.mapper.toLocationDetails
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.mapper.toNotificationModel
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.repo.alert_repo.AlertRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.platform.services.LocationService
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var alertRepository: AlertRepository
    private lateinit var locationService: LocationService

    private val fakeLocation = LocationDetails(lat = "30.0", long = "31.0", city = "Cairo")

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0

        mockkStatic("com.dmy.weather.data.mapper.WeatherMapperKt")
        mockkStatic("com.dmy.weather.data.mapper.HourlyForecastMapperKt")
        mockkStatic("com.dmy.weather.data.mapper.DailyForecastMapperKt")
        mockkStatic("com.dmy.weather.data.mapper.LocationMapperKt")
        mockkStatic("com.dmy.weather.data.mapper.FindMatchingAlertKt")


        weatherRemoteDataSource = mockk()
        settingsRepository = mockk()
        alertRepository = mockk()
        locationService = mockk()

        repository = WeatherRepositoryImpl(
            weatherRemoteDataSource,
            settingsRepository,
            alertRepository,
            locationService
        )
    }

    //Get Weather
    @Test
    fun getWeather_whenDataSourceReturnsData_returnsSuccess() = runTest {
        // Given
        val fakeDTO = mockk<WeatherDTO>()
        val fakeModel = mockk<WeatherModel>()

        coEvery { weatherRemoteDataSource.getCurrentWeather(fakeLocation) } returns fakeDTO

        every { fakeDTO.toModel() } returns fakeModel

        // When
        val result = repository.getWeather(fakeLocation)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeModel, result.getOrNull())
    }

    @Test
    fun getWeather_whenDataSourceReturnsNull_returnsNullDataException() = runTest {
        // Given
        coEvery { weatherRemoteDataSource.getCurrentWeather(fakeLocation) } returns null

        // When
        val result = repository.getWeather(fakeLocation)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun getWeather_whenDataSourceThrows_returnsFailure() = runTest {
        // Given
        coEvery { weatherRemoteDataSource.getCurrentWeather(fakeLocation) } throws Exception("Network Error")

        // When
        val result = repository.getWeather(fakeLocation)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }

    //Hourly Forecast
    @Test
    fun getHourlyForecast_whenDataSourceReturnsData_returnsSuccess() = runTest {
        // Given
        val fakeDTO = mockk<HourlyForecastDTO>()
        val fakeModel = mockk<HourlyForecastModel>()

        coEvery { weatherRemoteDataSource.getHourlyForecast(fakeLocation) } returns fakeDTO
        every { fakeDTO.toModel() } returns fakeModel

        // When
        val result = repository.getHourlyForecast(fakeLocation)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeModel, result.getOrNull())
    }

    @Test
    fun getHourlyForecast_whenDataSourceReturnsNull_returnsNullDataException() = runTest {
        // Given
        coEvery { weatherRemoteDataSource.getHourlyForecast(fakeLocation) } returns null

        // When
        val result = repository.getHourlyForecast(fakeLocation)

        // Then
        assertTrue(result.isFailure)
    }

    //Daily Forecast
    @Test
    fun getDailyForecast_whenDataSourceReturnsData_returnsSuccess() = runTest {
        // Given
        val fakeDTO = mockk<DailyForecastDTO>()
        val fakeModel = mockk<DailyForecastModel>()

        coEvery { weatherRemoteDataSource.getDailyForecast(fakeLocation) } returns fakeDTO
        every { fakeDTO.toModel() } returns fakeModel

        // When
        val result = repository.getDailyForecast(fakeLocation)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeModel, result.getOrNull())
    }

    @Test
    fun getDailyForecast_whenDataSourceReturnsNull_returnsNullDataException() = runTest {
        // Given
        coEvery { weatherRemoteDataSource.getDailyForecast(fakeLocation) } returns null

        // When
        val result = repository.getDailyForecast(fakeLocation)

        // Then
        assertTrue(result.isFailure)
    }

    // ALERT
    @Test
    fun getAlertWeather_whenLocationFromGPS_usesGPSLocation() = runTest {
        // Given
        val fakeGPSLocation = mockk<LatLng>()
        val fakeDTO = mockk<HourlyForecastDTO>()
        val fakeHourlyForecastItem = mockk<HourlyForecastItem>()
        val fakeNotificationModel = mockk<NotificationWeatherModel>()
        val fakeAlerts = listOf<AlertEntity>(mockk())
        val fakeAlertEntity = mockk<AlertEntity>()

        coEvery { locationService.getCurrentLocation() } returns fakeGPSLocation
        every { fakeGPSLocation.toLocationDetails() } returns fakeLocation

        coEvery { weatherRemoteDataSource.getHourlyForecast(fakeLocation) } returns fakeDTO
        coEvery { alertRepository.getActiveAlerts() } returns fakeAlerts

        every { fakeDTO.filterBasedOnAlerts(fakeAlerts) } returns (fakeHourlyForecastItem to fakeAlertEntity)
        every { fakeHourlyForecastItem.toNotificationModel(fakeDTO.city) } returns fakeNotificationModel

        // When
        val result = repository.getAlertWeather()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeNotificationModel, result.getOrNull()?.first)
        assertEquals(fakeAlertEntity, result.getOrNull()?.second)

        // Verify GPS location was used (settings not called)
        coVerify(exactly = 0) { settingsRepository.getLastKnownLocation() }
        coVerify(exactly = 0) { settingsRepository.getDefaultLocation() }
    }

    @Test
    fun getAlertWeather_whenFilterReturnsNull_returnsNullDataException() = runTest {
        // Given
        coEvery { locationService.getCurrentLocation() } returns null
        coEvery { settingsRepository.getLastKnownLocation() } returns fakeLocation

        val fakeDTO = mockk<HourlyForecastDTO>()
        val fakeAlerts = listOf<AlertEntity>(mockk())

        coEvery { weatherRemoteDataSource.getHourlyForecast(fakeLocation) } returns fakeDTO
        coEvery { alertRepository.getActiveAlerts() } returns fakeAlerts

        every { fakeDTO.filterBasedOnAlerts(fakeAlerts) } returns null

        // When
        val result = repository.getAlertWeather()

        // Then
        assertTrue(result.isFailure)
    }
}