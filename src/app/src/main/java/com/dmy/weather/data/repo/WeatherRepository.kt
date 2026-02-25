package com.dmy.weather.data.repo

import android.content.Context
import android.util.Log
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastItem
import com.dmy.weather.data.enums.AlertType.CLOUDS
import com.dmy.weather.data.enums.AlertType.HUMIDITY
import com.dmy.weather.data.enums.AlertType.PRESSURE
import com.dmy.weather.data.enums.AlertType.RAIN
import com.dmy.weather.data.enums.AlertType.SNOW
import com.dmy.weather.data.enums.AlertType.TEMP
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
            val pair = filterBasedOnAlerts(hourlyForecastDTO)
            val notificationWeatherModel = pair.first.toNotificationModel(hourlyForecastDTO.city)

            Log.i(TAG, "notificationWeatherModel: $notificationWeatherModel")

            notificationWeatherModel to pair.second
        }.mapFailure()
    }

    private suspend fun filterBasedOnAlerts(hourlyForecastDTO: HourlyForecastDTO): Pair<HourlyForecastItem, AlertEntity> {
        val activeAlerts = settingsRepository.getActiveAlerts()
        Log.i(TAG, "activeAlerts: $activeAlerts")
        hourlyForecastDTO.list?.forEach { item ->
            val matchedAlert = activeAlerts.firstOrNull { alert ->
                when (alert.alertType) {
                    TEMP ->
                        item.main?.temp?.let { return@firstOrNull it >= alert.max || it <= alert.min }
                            ?: false

                    HUMIDITY ->
                        item.main?.humidity?.let { return@firstOrNull it >= alert.max || it <= alert.min }
                            ?: false

                    PRESSURE ->
                        item.main?.pressure?.let { return@firstOrNull it >= alert.max || it <= alert.min }
                            ?: false

                    RAIN, SNOW, CLOUDS ->
                        return@firstOrNull item.weather?.firstOrNull()?.description == alert.alertType.desc

                    null -> false
                }
            }

            if (matchedAlert != null) return Pair(item, matchedAlert)
        }

        throw NullDataException()
    }
}

