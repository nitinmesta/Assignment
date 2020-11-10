package com.example.rakuten.data.model

data class Location(
    val name: String,
    val country: String,
    val localtime_epoch: Long,
    val utcOffset: String
)