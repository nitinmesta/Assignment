package com.example.rakuten.network.api

import androidx.lifecycle.LiveData
import com.example.rakuten.data.model.ApiResponse
import com.example.rakuten.data.model.WeatherResponse
import com.example.rakuten.network.adapter.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherApi {
    private var apiService: WeatherApiService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.weatherstack.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
        apiService = retrofit.create(WeatherApiService::class.java)
    }


    fun getWeatherDataForCity(cityName: String): LiveData<ApiResponse<WeatherResponse>> {
        return apiService.getWeatherForCity(cityName = cityName)
    }
}