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

    override suspend fun getCurrentWeather(locationDetails: LocationDetails?): WeatherDTO? {
        return when {
            locationDetails?.city != null ->
                weatherService.getCurrentWeather(
                    locationDetails.city
                )

            locationDetails?.long != null && locationDetails.lat != null ->
                weatherService.getCurrentWeather(
                    locationDetails.long, locationDetails.lat
                )

            else -> null
        }
    }


    override suspend fun getDailyForecast(locationDetails: LocationDetails?): DailyForecastDTO? {
        return when {
            locationDetails?.city != null ->
                weatherService.getDailyForecast(
                    locationDetails.city
                )

            locationDetails?.long != null && locationDetails.lat != null ->
                weatherService.getDailyForecast(
                    locationDetails.long, locationDetails.lat
                )

            else -> null
        }
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


    override suspend fun getClimateForecast(locationDetails: LocationDetails?): DailyForecastDTO? {
        return when {
            locationDetails?.city != null ->
                weatherService.getClimateForecast(
                    locationDetails.city
                )

            locationDetails?.long != null && locationDetails.lat != null ->
                weatherService.getClimateForecast(
                    locationDetails.long, locationDetails.lat
                )

            else -> null
        }
    }


}