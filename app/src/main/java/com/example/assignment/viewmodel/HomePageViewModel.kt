package com.example.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.convertDateToMillis
import com.example.assignment.data.db.WeatherDatabase
import com.example.assignment.data.db.entity.CityWeatherData
import com.example.assignment.data.model.ApiResponse
import com.example.assignment.data.model.WeatherApiSuccessResponse
import com.example.assignment.data.model.WeatherResponse
import com.example.assignment.network.api.WeatherApi

class HomePageViewModel(val weatherDatabase: WeatherDatabase) : ViewModel() {
    val weatherLiveData = weatherDatabase.cityWeatherDataDao().getAll()
    fun getWeatherData(cityName: String): LiveData<ApiResponse<WeatherResponse>> {
        return WeatherApi.getWeatherDataForCity(cityName)
    }

    fun storeNewData(it: WeatherApiSuccessResponse<WeatherResponse>) {
        it.data.also {
            val cityWeatherData = CityWeatherData(
                it.location?.name ?: "NA",
                it.current?.temperature ?: 0,
                convertDateToMillis(it.location?.localtime ?: "", "yyyy-MM-dd HH:mm"),
                it.current?.weather_descriptions?.getOrNull(0) ?: "NA",
                it.current?.weather_icons?.getOrNull(0) ?: ""
            )

            Thread {
                weatherDatabase.cityWeatherDataDao().insert(cityWeatherData)
            }.start()

        }
    }
}