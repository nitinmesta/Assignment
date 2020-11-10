package com.example.rakuten.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rakuten.R
import com.example.rakuten.WeatherApp
import com.example.rakuten.constants.CITY_WEATHER
import com.example.rakuten.data.db.entity.CityWeatherData
import com.example.rakuten.ui.screen.CITY_NAME_VIEW
import com.example.rakuten.ui.screen.CITY_TEMPERATURE
import com.example.rakuten.ui.screen.DetailsPage
import com.example.rakuten.ui.screen.WEATHER_ICON
import java.util.*
import kotlin.collections.ArrayList

class CityWeatherAdapter : RecyclerView.Adapter<CityWeatherAdapter.ViewHolder>() {
    private var cityDataWeatherList = arrayListOf<CityWeatherData>()

    fun setCityWeatherData(cityWeatherData: CityWeatherData) {
        cityDataWeatherList.add(cityWeatherData)
        notifyItemInserted(cityDataWeatherList.size - 1)
    }

    fun setCityWeatherData(newCityDataWeatherList: ArrayList<CityWeatherData>, query: String?) {
        if (cityDataWeatherList.isEmpty()) {
            this.cityDataWeatherList = newCityDataWeatherList

        } else {
            val oldList = ArrayList(cityDataWeatherList)
            val newList = ArrayList<CityWeatherData>(newCityDataWeatherList)
            // this logic is to to bring the newly added item onto the top
            oldList.retainAll(newList)
            newList.removeAll(oldList)
            if (newList.isNotEmpty()) {
                oldList.addAll(0, newList)
                notifyItemRangeInserted(0, newList.size)
            } else {
                //no info changed so bring the queried item to front
                val existingObject =
                    oldList.map { it.cityName.toLowerCase(Locale.getDefault()) to it }
                        .toMap()[query?.trim()?.toLowerCase(Locale.getDefault())]
                val existingObjectIndex = oldList.indexOf(existingObject)
                if (existingObjectIndex != -1 && existingObjectIndex != 0) {
                    Collections.swap(oldList, 0, existingObjectIndex)
                    notifyItemMoved(existingObjectIndex, 0)
                }
            }
            this.cityDataWeatherList = oldList

        }
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val cityNameTextView = view.findViewById<AppCompatTextView>(R.id.city_name)
        private val temperatureTextView = view.findViewById<AppCompatTextView>(R.id.temp)
        private val descriptionTextView = view.findViewById<AppCompatTextView>(R.id.description)
        private val weatherImageView = view.findViewById<AppCompatImageView>(R.id.weather_icon)

        fun bind(cityWeatherData: CityWeatherData) {
            cityNameTextView.text = cityWeatherData.cityName
            temperatureTextView.text = "${cityWeatherData.temp}\u2103"
            descriptionTextView.text = cityWeatherData.weatherDescription
            addUrlToSet(cityWeatherData.weatherUrl)
            if (cityWeatherData.weatherUrl.isNotEmpty()) {
                Glide.with(view.context).load(cityWeatherData.weatherUrl)
                    .circleCrop()
                    .into(weatherImageView)
            }

            view.setOnClickListener {
                val detailsIntent = Intent(view.context, DetailsPage::class.java)
                detailsIntent.putExtra(CITY_WEATHER, cityWeatherData)
                ContextCompat.startActivity(
                    view.context,
                    detailsIntent,
                    getAnimationOptions(view.context)
                )
            }
        }

        private fun addUrlToSet(weatherUrl: String) {
            val weatherApp = view.context.applicationContext
            if (weatherApp is WeatherApp) {
                weatherApp.weatherIconSet.add(weatherUrl)
            }
        }

        private fun getAnimationOptions(context: Context): Bundle? {
            val animationOption = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (view.context as Activity),
                Pair(weatherImageView, WEATHER_ICON),
                Pair(cityNameTextView, CITY_NAME_VIEW),
                Pair(temperatureTextView, CITY_TEMPERATURE)
            )
            return animationOption.toBundle()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_data_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cityDataWeatherList[position])
    }

    override fun getItemCount(): Int {
        return cityDataWeatherList.size
    }
}