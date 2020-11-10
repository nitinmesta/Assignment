package com.example.rakuten.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rakuten.R
import com.example.rakuten.WeatherApp
import com.example.rakuten.constants.CITY_WEATHER
import com.example.rakuten.data.db.entity.CityWeatherData
import com.example.rakuten.ui.adapter.WeatherDetailsAdapter
import java.util.*

val CITY_NAME_VIEW = "city_name_text_view"
val WEATHER_ICON = "city_weather_icon"
val CITY_TEMPERATURE = "city_temperature"

class DetailsPage : AppCompatActivity() {


    var cityWeatherData: CityWeatherData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_page)
        cityWeatherData = intent?.extras?.getParcelable(CITY_WEATHER) ?: return finish()

        cityWeatherData?.let {
            val cityNameTextView = findViewById<AppCompatTextView>(R.id.city_name)
            val cityTempTextView = findViewById<AppCompatTextView>(R.id.city_temp)
            val weatherImageView = findViewById<AppCompatImageView>(R.id.weather_icon)
            val weatherForecastRecyclerView = findViewById<RecyclerView>(R.id.weather_forecast)
            weatherForecastRecyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = WeatherDetailsAdapter()
            adapter.data = constructData(it)
            weatherForecastRecyclerView.adapter = adapter
            cityNameTextView.text = it.cityName
            cityTempTextView.text = "${it.temp}\u2103"

            Glide.with(this).load(it.weatherUrl)
                .circleCrop()
                .into(weatherImageView)
            ViewCompat.setTransitionName(cityNameTextView, CITY_NAME_VIEW)
            ViewCompat.setTransitionName(cityTempTextView, CITY_TEMPERATURE)
            ViewCompat.setTransitionName(weatherImageView, WEATHER_ICON)
        }
    }

    private fun constructData(cityWeatherData: CityWeatherData): List<Triple<String, Short, String>> {
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
        val weatherApp = application
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
            Calendar.SUNDAY -> getString(R.string.sunday)
            Calendar.MONDAY -> getString(R.string.monday)
            Calendar.TUESDAY -> getString(R.string.tuesday)
            Calendar.WEDNESDAY -> getString(R.string.wednessday)
            Calendar.THURSDAY -> getString(R.string.thursday)
            Calendar.FRIDAY -> getString(R.string.friday)
            Calendar.SATURDAY -> getString(R.string.saturday)
            else -> getString(R.string.sunday)
        }
    }
}