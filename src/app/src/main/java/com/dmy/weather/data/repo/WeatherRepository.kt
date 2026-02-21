package com.dmy.weather.data.repo

import android.util.Log
import com.dmy.weather.data.data_source.remote.GeocodingRemoteDataSource
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.utils.exceptions.NullDataException
import com.dmy.weather.utils.mapFailure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherRepository(
    val weatherRemoteDataSource: WeatherRemoteDataSource,
    val geocodingRemoteDataSource: GeocodingRemoteDataSource,
    val settingsRepository: SettingsRepository,
) {
    companion object {
        private const val TAG = "WeatherRepo"
    }

    suspend fun getCurrentWeather(locationDetails: LocationDetails): Result<WeatherModel> {
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
            val hourlyForecastDTO =
                when {
                    locationDetails.city != null ->
                        weatherRemoteDataSource.getHourlyForecast(
                            locationDetails.city
                        )

                    locationDetails.long != null && locationDetails.lat != null ->
                        weatherRemoteDataSource.getHourlyForecast(
                            locationDetails.long, locationDetails.lat
                        )

                    else -> null
                } ?: throw NullDataException()

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

    suspend fun getClimateForecast(city: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getClimateForecast(city)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")
    }

    suspend fun getClimateForecast(long: String, lat: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getClimateForecast(long, lat)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")
    }


    fun getGeocodingCityInfoByCity(city: String) {
        GlobalScope.launch {

            val geocodingCityDTO = geocodingRemoteDataSource.getGeocodingCityByCity(city)
            val cityModel = geocodingCityDTO?.toModel()

            Log.i(TAG, "geocodingCityDTO: $geocodingCityDTO")
            Log.i(TAG, "cityModel: $cityModel")

//            return cityModel
        }
    }

    suspend fun getGeocodingCityInfoByCoord(long: String, lat: String): CityModel? {
        val geocodingCityDTO = geocodingRemoteDataSource.getGeocodingCityByCoord(long, lat)
        val cityModel = geocodingCityDTO?.toModel()

        Log.i(TAG, "geocodingCityDTO: $geocodingCityDTO")
        Log.i(TAG, "cityModel: $cityModel")

        return cityModel
    }
}

