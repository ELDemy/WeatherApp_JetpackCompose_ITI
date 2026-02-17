package com.dmy.weather.data.repo

import android.content.Context
import android.util.Log
import com.dmy.weather.data.data_source.remote.GeocodingRemoteDataSource
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.mapper.toModel
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.utils.exceptions.NullDataException
import com.dmy.weather.utils.mapFailure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherRepo(context: Context) {
    companion object {
        private const val TAG = "WeatherRepo"
        private val weatherRemoteDataSource = WeatherRemoteDataSource()
        private val geocodingRemoteDataSource = GeocodingRemoteDataSource()
    }

    suspend fun getCurrentWeather(city: String): Result<WeatherModel> {
        return runCatching {
            val weatherDTO = weatherRemoteDataSource.getCurrentWeather(city)
                ?: throw NullDataException()

            val weatherModel = weatherDTO.toModel()

            Log.i(TAG, "weatherDTO: $weatherDTO")
            Log.i(TAG, "weatherModel: $weatherModel")

            return Result.success(weatherModel)
        }.mapFailure()
    }


    suspend fun getCurrentWeather(long: String, lat: String): Result<WeatherModel> {
        return runCatching {
            val weatherDTO = weatherRemoteDataSource.getCurrentWeather(long, lat)
                ?: throw NullDataException()

            val weatherModel = weatherDTO.toModel()

            Log.i(TAG, "weatherDTO: $weatherDTO")
            Log.i(TAG, "weatherModel: $weatherModel")
            return Result.success(weatherModel)
        }.mapFailure()
    }

    suspend fun getHourlyForecast(city: String): Result<HourlyForecastModel> {
        return runCatching {
            val hourlyForecastDTO =
                weatherRemoteDataSource.getHourlyForecast(city) ?: throw NullDataException()
            val hourlyForecastModel = hourlyForecastDTO.toModel()

            Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")
            Log.i(TAG, "hourlyForecastModel: $hourlyForecastModel")

            return Result.success(hourlyForecastModel)
        }.mapFailure()

    }

    suspend fun getHourlyForecast(long: String, lat: String): Result<HourlyForecastModel> {
        return runCatching {
            val hourlyForecastDTO =
                weatherRemoteDataSource.getHourlyForecast(long, lat) ?: throw NullDataException()
            
            val hourlyForecastModel = hourlyForecastDTO.toModel()

            Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")
            Log.i(TAG, "hourlyForecastModel: $hourlyForecastModel")
            return Result.success(hourlyForecastModel)
        }.mapFailure()
    }

    suspend fun getDailyForecast(city: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getDailyForecast(city)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")

    }

    suspend fun getDailyForecast(long: String, lat: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getDailyForecast(long, lat)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")

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

