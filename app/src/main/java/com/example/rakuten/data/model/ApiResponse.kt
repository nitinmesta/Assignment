package com.example.rakuten.data.model


import retrofit2.Response

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                when (val body = response.body()) {
                    is WeatherResponse -> {
                        if (body.error != null) {
                            WeatherApiErrorResponse(WeatherErrorResponse(body.success, body.error))
                        } else {
                            WeatherApiSuccessResponse(WeatherAttribute(body.location, body.current))
                        }
                    }
                    else -> WeatherApiUnknownError()
                }
            } else {
                WeatherApiUnknownError()
            }
        }

        fun <T> create(errorCode: Int, throwable: Throwable): ApiResponse<T> {
            return WeatherApiUnknownError()
        }
    }
}

data class WeatherApiSuccessResponse<T>(val data: WeatherAttribute) : ApiResponse<T>()

data class WeatherApiErrorResponse<T>(val data: WeatherErrorResponse) :
    ApiResponse<T>()

class WeatherApiUnknownError<T> : ApiResponse<T>()