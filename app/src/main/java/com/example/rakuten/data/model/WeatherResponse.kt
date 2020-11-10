package com.example.rakuten.data.model

data class WeatherResponse(
    val success: Boolean? = true,
    val location: Location?,
    val current: WeatherData?,
    val error: ApiError?
)

data class WeatherAttribute(
    val location: Location?,
    val current: WeatherData?
)

data class WeatherData(
    val temperature: Short,
    val weather_code: Int, val weather_icons: Array<String>, val weather_descriptions: Array<String>
)
