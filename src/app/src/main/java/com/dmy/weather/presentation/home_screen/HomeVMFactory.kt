package com.dmy.weather.presentation.home_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmy.weather.data.repo.WeatherRepo

class HomeVMFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = WeatherRepo(context)

        return HomeVM(repo) as T
    }
}