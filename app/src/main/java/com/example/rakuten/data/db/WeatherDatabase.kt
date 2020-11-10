package com.example.rakuten.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rakuten.data.db.dao.CityWeatherDao
import com.example.rakuten.data.db.entity.CityWeatherData

@Database(entities = [CityWeatherData::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun cityWeatherDataDao(): CityWeatherDao
}