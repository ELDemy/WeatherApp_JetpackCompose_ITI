package com.dmy.weather.data.repo.weather_repo

import android.util.Log
import com.dmy.weather.data.data_source.remote.weather_data_source.WeatherRemoteDataSource
import com.dmy.weather.data.mapper.filterBasedOnAlerts
import com.dmy.weather.data.mapper.toLocationDetails
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.mapper.toNotificationModel
import com.dmy.weather.data.model.AlertModel
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.platform.services.LocationService
import com.dmy.weather.utils.exceptions.NullDataException
import com.dmy.weather.utils.mapFailure

private const val TAG = "WeatherRepo"

class WeatherRepositoryImpl(
    val weatherRemoteDataSource: WeatherRemoteDataSource,
    val settingsRepository: SettingsRepository,
    val locationService: LocationService
) : WeatherRepository {

    override suspend fun getWeather(locationDetails: LocationDetails): Result<WeatherModel> {
        return runCatching {
            val weatherDTO = weatherRemoteDataSource.getCurrentWeather(locationDetails)
                ?: throw NullDataException()

            val weatherModel = weatherDTO.toModel()

            Log.i(TAG, "weatherDTO: $weatherDTO")
            Log.i(TAG, "weatherModel: $weatherModel")

            weatherModel
        }.mapFailure()
    }

    override suspend fun getHourlyForecast(locationDetails: LocationDetails): Result<HourlyForecastModel> {
        return runCatching {
            val hourlyForecastDTO = weatherRemoteDataSource.getHourlyForecast(locationDetails)
                ?: throw NullDataException()

            val hourlyForecastModel = hourlyForecastDTO.toModel()

            Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")
            Log.i(TAG, "hourlyForecastModel: $hourlyForecastModel")

            hourlyForecastModel
        }.mapFailure()
    }

    override suspend fun getDailyForecast(locationDetails: LocationDetails): Result<DailyForecastModel> {
        return runCatching {
            val dailyForecastDTO = weatherRemoteDataSource.getDailyForecast(locationDetails)
                ?: throw NullDataException()

            val dailyForecastModel = dailyForecastDTO.toModel()

            Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
            Log.i(TAG, "weatherModel: $dailyForecastModel")

            dailyForecastModel
        }.mapFailure()
    }

    override suspend fun getWeatherAlerts(activeAlerts: List<AlertModel>): Result<List<Pair<NotificationWeatherModel, AlertModel>>> {
        return runCatching {
            val locationDetails =
                locationService.getCurrentLocation()?.toLocationDetails()
                    ?: settingsRepository.getLastKnownLocation()
                    ?: settingsRepository.getDefaultLocation()

            val hourlyForecastDTO = weatherRemoteDataSource.getHourlyForecast(locationDetails)
                ?: throw NullDataException()
            Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")

            activeAlerts.map { alert ->
                val pair = hourlyForecastDTO.filterBasedOnAlerts(alert)
                    ?: throw NullDataException()

                val notificationWeatherModel =
                    pair.first.toNotificationModel(hourlyForecastDTO.city)

                Log.i(TAG, "notificationWeatherModel: $notificationWeatherModel")

                notificationWeatherModel to pair.second
            }

        }.mapFailure()
    }

}

