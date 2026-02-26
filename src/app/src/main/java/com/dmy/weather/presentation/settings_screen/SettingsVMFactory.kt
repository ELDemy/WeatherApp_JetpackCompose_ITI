package com.dmy.weather.presentation.settings_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSource
import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.repo.settings_repo.SettingsRepositoryImpl
import org.koin.java.KoinJavaComponent.inject

class SettingsVMFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val settingsDataSource: SettingsDataSource by inject(SettingsDataSource::class.java)
        val alertDao: AlertDao by inject(AlertDao::class.java)

        return SettingsVM(SettingsRepositoryImpl(settingsDataSource)) as T
    }
}