package com.example.rakuten.data

data class Location(
    val name: String,
    val country: String,
    val localtime_epoch: Long,
    val utcOffset: String
)