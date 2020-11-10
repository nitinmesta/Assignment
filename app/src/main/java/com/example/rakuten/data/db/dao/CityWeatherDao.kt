package com.example.rakuten.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rakuten.data.db.entity.CityWeatherData

@Dao
interface CityWeatherDao {
    @Query("select * from cityweatherdata ORDER BY currentTimeStamp DESC")
    fun getAll(): LiveData<List<CityWeatherData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityWeatherData: CityWeatherData)
}