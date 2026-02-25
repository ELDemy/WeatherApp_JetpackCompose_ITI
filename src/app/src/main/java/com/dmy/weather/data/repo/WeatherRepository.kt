package com.dmy.weather.data.repo

import android.content.Context
import android.util.Log
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.mapper.filterBasedOnAlerts
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.mapper.toNotificationModel
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.model.toLocationDetails
import com.dmy.weather.platform.services.LocationServices
import com.dmy.weather.utils.exceptions.NullDataException
import com.dmy.weather.utils.mapFailure

class WeatherRepository(
    val weatherRemoteDataSource: WeatherRemoteDataSource,
    val settingsRepository: SettingsRepository,
    val alertRepository: AlertRepository,
    val context: Context
) {
    companion object {
        private const val TAG = "WeatherRepo"
    }

    suspend fun getCurrentWeather(): Result<WeatherModel> {
        return runCatching {
            val locationDetails =
                settingsRepository.getLastKnownLocation() ?: settingsRepository.getDefaultLocation()

            val weatherDTO =
                when {
                    locationDetails?.city != null ->
                        weatherRemoteDataSource.getCurrentWeather(
                            locationDetails.city
                        )

                    locationDetails?.long != null && locationDetails.lat != null ->
                        weatherRemoteDataSource.getCurrentWeather(
                            locationDetails.long, locationDetails.lat
                        )

                    else -> null
                } ?: throw NullDataException()

            val weatherModel = weatherDTO.toModel()

            Log.i(TAG, "weatherDTO: $weatherDTO")
            Log.i(TAG, "weatherModel: $weatherModel")

            return Result.success(weatherModel)
        }.mapFailure()
    }

    suspend fun getWeather(locationDetails: LocationDetails): Result<WeatherModel> {
        return runCatching {
            val weatherDTO =
                when {
                    locationDetails.city != null ->
                        weatherRemoteDataSource.getCurrentWeather(
                            locationDetails.city
                        )

                    locationDetails.long != null && locationDetails.lat != null ->
                        weatherRemoteDataSource.getCurrentWeather(
                            locationDetails.long, locationDetails.lat
                        )

                    else -> null
                } ?: throw NullDataException()

            val weatherModel = weatherDTO.toModel()

            Log.i(TAG, "weatherDTO: $weatherDTO")
            Log.i(TAG, "weatherModel: $weatherModel")

            return Result.success(weatherModel)
        }.mapFailure()
    }

    suspend fun getHourlyForecast(locationDetails: LocationDetails): Result<HourlyForecastModel> {
        return runCatching {
            val hourlyForecastDTO = weatherRemoteDataSource.getHourlyForecast(locationDetails)
                ?: throw NullDataException()

            val hourlyForecastModel = hourlyForecastDTO.toModel()

            Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")
            Log.i(TAG, "hourlyForecastModel: $hourlyForecastModel")

            return Result.success(hourlyForecastModel)
        }.mapFailure()
    }

    suspend fun getDailyForecast(locationDetails: LocationDetails): Result<DailyForecastModel> {
        return runCatching {
            val dailyForecastDTO =
                when {
                    locationDetails.city != null ->
                        weatherRemoteDataSource.getDailyForecast(
                            locationDetails.city
                        )

                    locationDetails.long != null && locationDetails.lat != null ->
                        weatherRemoteDataSource.getDailyForecast(
                            locationDetails.long, locationDetails.lat
                        )

                    else -> null
                } ?: throw NullDataException()

            val dailyForecastModel = dailyForecastDTO.toModel()

            Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
            Log.i(TAG, "weatherModel: $dailyForecastModel")
            return Result.success(dailyForecastModel)
        }.mapFailure()
    }

    suspend fun getAlertWeather(): Result<Pair<NotificationWeatherModel, AlertEntity>> {
        return runCatching {
            val locationDetails =
                LocationServices.getCurrentLocation(context)?.toLocationDetails()
                    ?: settingsRepository.getLastKnownLocation()
                    ?: settingsRepository.getDefaultLocation()

            val hourlyForecastDTO = weatherRemoteDataSource.getHourlyForecast(locationDetails)
                ?: throw NullDataException()
            Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")

            val activeAlerts = alertRepository.getActiveAlerts()
            Log.i(TAG, "activeAlerts: $activeAlerts")

            val pair = hourlyForecastDTO.filterBasedOnAlerts(activeAlerts)
                ?: throw NullDataException()

            val notificationWeatherModel = pair.first.toNotificationModel(hourlyForecastDTO.city)

            Log.i(TAG, "notificationWeatherModel: $notificationWeatherModel")

            notificationWeatherModel to pair.second
        }.mapFailure()
    }

}

