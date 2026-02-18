package com.dmy.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.dmy.weather.presentation.my_app.WeatherApp
import com.dmy.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.white))
                ) { innerPadding ->
                    WeatherApp(
                        Modifier
                            .padding(innerPadding)
                            .background(color = colorResource(R.color.white))
                    )
                }
            }
        }
    }
}
