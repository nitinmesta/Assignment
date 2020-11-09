package com.example.rakuten.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rakuten.data.ApiResponse
import com.example.rakuten.data.WeatherResponse
import com.example.rakuten.network.api.WeatherApi

class HomePageViewModel : ViewModel() {
    fun getWeatherData(cityName: String):LiveData<ApiResponse<WeatherResponse>> {
        return WeatherApi.getWeatherDataForCity(cityName)
    }
}