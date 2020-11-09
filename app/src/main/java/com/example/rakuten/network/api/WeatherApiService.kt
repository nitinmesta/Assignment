package com.example.rakuten.network.api

import androidx.lifecycle.LiveData
import com.example.rakuten.data.ApiResponse
import com.example.rakuten.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/current")
    fun  getWeatherForCity(
        @Query("access_key") accessKey: String = "7930181b912ecd90b239e54095466f12",
        @Query("query") cityName: String
    ): LiveData<ApiResponse<WeatherResponse>>
}