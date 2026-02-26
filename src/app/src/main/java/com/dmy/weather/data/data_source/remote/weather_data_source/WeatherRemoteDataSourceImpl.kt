package com.dmy.weather.data.data_source.remote.weather_data_source

import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.network.WeatherService

class WeatherRemoteDataSourceImpl(
    val weatherService: WeatherService
) : WeatherRemoteDataSource {
    companion object {
        private const val TAG = "WeatherRemoteDataSource"
    }

    override suspend fun getCurrentWeather(city: String): WeatherDTO? {
        return weatherService.getCurrentWeather(city)
    }

    override suspend fun getCurrentWeather(long: String, lat: String): WeatherDTO? {
        return weatherService.getCurrentWeather(long, lat)
    }

    override suspend fun getDailyForecast(city: String): DailyForecastDTO? {
        return weatherService.getDailyForecast(city)
    }

    override suspend fun getDailyForecast(long: String, lat: String): DailyForecastDTO? {
        return weatherService.getDailyForecast(long, lat)
    }

    override suspend fun getClimateForecast(city: String): DailyForecastDTO? {
        return weatherService.getClimateForecast(city)
    }

    override suspend fun getClimateForecast(long: String, lat: String): DailyForecastDTO? {
        return weatherService.getClimateForecast(long, lat)
    }

    override suspend fun getHourlyForecast(locationDetails: LocationDetails?): HourlyForecastDTO? {
        return when {
            locationDetails?.city != null ->
                weatherService.getHourlyForecast(
                    locationDetails.city
                )

            locationDetails?.long != null && locationDetails.lat != null ->
                weatherService.getHourlyForecast(
                    locationDetails.long, locationDetails.lat
                )

            else -> null
        }
    }

    override suspend fun getHourlyForecast(city: String): HourlyForecastDTO? {
        return weatherService.getHourlyForecast(city)
    }

    override suspend fun getHourlyForecast(long: String, lat: String): HourlyForecastDTO? {
        return weatherService.getHourlyForecast(long, lat)
    }


}