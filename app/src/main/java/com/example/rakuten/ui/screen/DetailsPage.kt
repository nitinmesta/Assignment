package com.example.rakuten.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.example.rakuten.R
import com.example.rakuten.constants.CITY_WEATHER
import com.example.rakuten.data.db.entity.CityWeatherData

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