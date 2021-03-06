package com.example.assignment

import android.app.Application
import androidx.room.Room
import com.example.assignment.data.db.WeatherDatabase

class WeatherApp : Application() {
    lateinit var weatherDatabase: WeatherDatabase
    val weatherIconSet = HashSet<String>()
    override fun onCreate() {
        super.onCreate()
        weatherDatabase = Room.databaseBuilder(
            this, WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }
}