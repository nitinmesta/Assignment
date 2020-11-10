package com.example.rakuten.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rakuten.R

class WeatherDetailsAdapter : RecyclerView.Adapter<WeatherDetailsAdapter.ViewHolder>() {
    var data: List<Triple<String, Short, String>> = arrayListOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: AppCompatTextView = view.findViewById(R.id.day)
        val temperatureTextView: AppCompatTextView = view.findViewById(R.id.temp)
        val weatheImageView: AppCompatImageView =
            view.findViewById(R.id.weather_icon)

        fun bind(dayData: Triple<String, Short, String>) {
            dayTextView.text = dayData.first
            temperatureTextView.text = "${dayData.second}\u2103"
            Glide.with(view.context).load(dayData.third).circleCrop().into(weatheImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.weather_day_item, parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}