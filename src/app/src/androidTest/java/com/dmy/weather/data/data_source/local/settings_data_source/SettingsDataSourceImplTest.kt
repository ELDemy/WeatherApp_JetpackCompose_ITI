package com.dmy.weather.data.data_source.local.settings_data_source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSourceImpl.Companion.dataStore
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsDataSourceImplTest {

    private lateinit var dataSource: SettingsDataSource
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        dataSource = SettingsDataSourceImpl(context)
    }

    @After
    fun tearDown() = runTest {
        context.dataStore.edit { it.clear() }
    }


    @Test
    fun saveSettings_andRead_returnsCorrectSettings() = runTest {
        // Given
        val settings = UserSettings(
            lang = AppLanguage.AR,
            unit = UnitSystem.IMPERIAL,
            locationMode = LocationMode.MAP
        )

        // When
        dataSource.saveSettings(settings)
        val result = dataSource.settingsFlow.first()

        // Then
        assertThat(result, `is`(settings))
        assertThat(result.lang, `is`(AppLanguage.AR))
        assertThat(result.unit, `is`(UnitSystem.IMPERIAL))
        assertThat(result.locationMode, `is`(LocationMode.MAP))
    }

    @Test
    fun saveDefaultLocation_andRead_returnsCorrectLocation() = runTest {
        // Given
        val location = LocationDetails(lat = "30", long = "31")

        // When
        dataSource.saveDefaultLocation(location)
        val result = dataSource.defaultLocationFlow.first()

        // Then
        assertThat(result, `is`(location))
    }

    @Test
    fun defaultLocation_whenNotSet_returnsNull() = runTest {
        // When
        val result = dataSource.defaultLocationFlow.first()

        // Then
        assertThat(result, nullValue())
    }

    @Test
    fun saveLastKnownLocation_andRead_returnsCorrectLocation() = runTest {
        // Given
        val location = LocationDetails(lat = "30", long = "55.0")

        // When
        dataSource.saveLastKnownLocation(location)
        val result = dataSource.lastKnownLocationFlow.first()

        // Then
        assertThat(result, `is`(location))
    }
}