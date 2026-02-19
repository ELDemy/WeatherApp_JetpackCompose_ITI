package com.dmy.weather.presentation.settings_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmy.weather.data.data_source.local.data_store.MyDataStore
import com.dmy.weather.data.repo.SettingsRepository

class SettingsVMFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsVM(SettingsRepository(MyDataStore(context))) as T
    }
}