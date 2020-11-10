package com.example.assignment.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityWeatherData(
    @PrimaryKey val cityName: String,
    val temp: Short,
    val currentTimeStamp: Long,
    val weatherDescription: String,
    val weatherUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt().toShort(),
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cityName)
        parcel.writeInt(temp.toInt())
        parcel.writeLong(currentTimeStamp)
        parcel.writeString(weatherDescription)
        parcel.writeString(weatherUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityWeatherData> {
        override fun createFromParcel(parcel: Parcel): CityWeatherData {
            return CityWeatherData(parcel)
        }

        override fun newArray(size: Int): Array<CityWeatherData?> {
            return arrayOfNulls(size)
        }
    }

}