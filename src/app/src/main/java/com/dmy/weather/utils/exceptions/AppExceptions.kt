package com.dmy.weather.utils.exceptions

class NullDataException(override val message: String = "No data returned") : Exception(message)

