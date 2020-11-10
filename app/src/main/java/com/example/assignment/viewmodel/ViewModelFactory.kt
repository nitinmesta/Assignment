package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.WeatherApp

class ViewModelFactory constructor(private val weatherApp: WeatherApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomePageViewModel::class.java) -> HomePageViewModel(
                weatherApp.weatherDatabase
            ) as T
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> DetailsViewModel(weatherApp) as T
            else -> null as T
        }
    }
}