package com.dmy.weather.utils

import android.util.Log
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionMapper {
    private const val TAG = "ExceptionMapper"
    fun map(exception: Throwable?): String {
        Log.i(TAG, "ExceptionMapper: ${exception?.stackTrace}")
        
        return when (exception) {
            is MyException -> exception.message
            is UnknownHostException -> "No internet connection"
            is SocketTimeoutException -> "Request timed out"
            is HttpException -> when (exception.code()) {
                401 -> "Unauthorized, please login again"
                404 -> "Not found"
                500 -> "Server error, try again later"
                else -> "Something went wrong"
            }

            else -> exception?.message ?: "Unknown error"
        }
    }
}

class MyException(override val message: String) : Exception(message)