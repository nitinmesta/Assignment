package com.example.rakuten

import android.app.Application
import androidx.room.Room
import com.example.rakuten.data.db.WeatherDatabase

class WeatherApp : Application() {
    lateinit var weatherDatabase: WeatherDatabase
    override fun onCreate() {
        super.onCreate()
        weatherDatabase = Room.databaseBuilder(
            this, WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }
}