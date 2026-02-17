package com.dmy.weather.utils

fun <T> Result<T>.mapFailure(): Result<T> {
    return if (isFailure)
        Result.failure(Exception(ExceptionMapper.map(exceptionOrNull())))
    else this
}

