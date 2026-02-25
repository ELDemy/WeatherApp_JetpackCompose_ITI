package com.dmy.weather.presentation.settings_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmy.weather.data.data_source.local.data_store.MyDataStore
import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.repo.SettingsRepository
import org.koin.java.KoinJavaComponent.inject

class SettingsVMFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val myDataStore: MyDataStore by inject(MyDataStore::class.java)
        val alertDao: AlertDao by inject(AlertDao::class.java)

        return SettingsVM(SettingsRepository(myDataStore, alertDao)) as T
    }
}