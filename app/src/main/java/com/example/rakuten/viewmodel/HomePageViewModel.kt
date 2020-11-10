package com.example.rakuten.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rakuten.WeatherApp
import com.example.rakuten.data.db.WeatherDatabase
import com.example.rakuten.data.db.entity.CityWeatherData
import com.example.rakuten.data.model.ApiResponse
import com.example.rakuten.data.model.WeatherApiSuccessResponse
import com.example.rakuten.data.model.WeatherResponse
import com.example.rakuten.network.api.WeatherApi

class HomePageViewModel(val weatherDatabase: WeatherDatabase) : ViewModel() {
    val weatherLiveData = weatherDatabase.cityWeatherDataDao().getAll()
    fun getWeatherData(cityName: String): LiveData<ApiResponse<WeatherResponse>> {
        return WeatherApi.getWeatherDataForCity(cityName)
    }

    fun storeNewData(it: WeatherApiSuccessResponse<WeatherResponse>) {
        it.data.also {
            val cityWeatherData = CityWeatherData(
                it.location?.name ?: "NA",
                it.current?.temperature ?: 0, it.location?.localtime_epoch ?: 0,
                it.current?.weather_descriptions?.getOrNull(0) ?: "NA",
                it.current?.weather_icons?.getOrNull(0) ?: ""
            )

            Thread {
                weatherDatabase.cityWeatherDataDao().insert(cityWeatherData)
            }.start()

        }
    }
}