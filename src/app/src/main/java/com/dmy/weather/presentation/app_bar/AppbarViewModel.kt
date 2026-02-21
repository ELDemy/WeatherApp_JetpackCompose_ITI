package com.dmy.weather.presentation.app_bar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppbarViewModel : ViewModel() {
    var background by mutableStateOf<Int?>(null)

    fun updateBackground(bg: Int?) {
        background = bg
    }
}