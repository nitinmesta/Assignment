package com.example.assignment.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.WeatherApp
import com.example.assignment.constants.CITY_WEATHER
import com.example.assignment.data.db.entity.CityWeatherData
import com.example.assignment.ui.adapter.WeatherDetailsAdapter
import com.example.assignment.viewmodel.DetailsViewModel
import com.example.assignment.viewmodel.HomePageViewModel
import com.example.assignment.viewmodel.ViewModelFactory
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
            val viewModel = ViewModelProvider(this, ViewModelFactory(application as WeatherApp)).get(
                DetailsViewModel::class.java
            )
            val adapter = WeatherDetailsAdapter()
            adapter.data = viewModel.constructData(it)
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
}