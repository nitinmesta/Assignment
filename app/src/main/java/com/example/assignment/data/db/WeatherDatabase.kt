package com.example.assignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.data.db.dao.CityWeatherDao
import com.example.assignment.data.db.entity.CityWeatherData

@Database(entities = [CityWeatherData::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun cityWeatherDataDao(): CityWeatherDao
}