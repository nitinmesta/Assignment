package com.example.assignment.viewmodel

import androidx.lifecycle.AndroidViewModel
import com.example.assignment.R
import com.example.assignment.WeatherApp
import com.example.assignment.data.db.entity.CityWeatherData
import java.util.*

class DetailsViewModel(val app: WeatherApp) : AndroidViewModel(app) {
    fun constructData(cityWeatherData: CityWeatherData): List<Triple<String, Short, String>> {
        val dataSet = arrayListOf<Triple<String, Short, String>>()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = cityWeatherData.currentTimeStamp
        var data = Triple(
            getDayText((calendar.get(Calendar.DAY_OF_WEEK))),
            cityWeatherData.temp,
            cityWeatherData.weatherUrl
        )
        dataSet.add(data)
        //Create fake next 6 days data
        for (index in 1..6) {
            calendar.add(Calendar.DATE, 1)
            data = Triple(
                getDayText((calendar.get(Calendar.DAY_OF_WEEK))),
                getFakeTemp(cityWeatherData.temp.toInt()),
                getFakeUrl(cityWeatherData.weatherUrl)
            )
            dataSet.add(data)
        }

        return dataSet
    }

    private fun getFakeUrl(weatherUrl: String): String {
        val weatherApp = app
        if (weatherApp is WeatherApp) {
            val urlIndex = Random().nextInt(weatherApp.weatherIconSet.size)
            return weatherApp.weatherIconSet.toArray()[urlIndex].toString()
        }
        return weatherUrl
    }

    private fun getFakeTemp(temp: Int): Short {
        val factor = Random().nextInt(5)
        return (if (factor % 2 == 0) {
            temp + factor
        } else {
            temp - factor
        }).toShort()
    }

    private fun getDayText(day: Int): String {
        return when ((day)) {
            Calendar.SUNDAY -> app.getString(R.string.sunday)
            Calendar.MONDAY -> app.getString(R.string.monday)
            Calendar.TUESDAY -> app.getString(R.string.tuesday)
            Calendar.WEDNESDAY -> app.getString(R.string.wednessday)
            Calendar.THURSDAY -> app.getString(R.string.thursday)
            Calendar.FRIDAY -> app.getString(R.string.friday)
            Calendar.SATURDAY -> app.getString(R.string.saturday)
            else -> app.getString(R.string.sunday)
        }
    }

}