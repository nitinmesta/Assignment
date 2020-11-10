package com.example.rakuten

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun convertDateToMillis(input: String, format: String): Long {
    return if (input.isNotEmpty()) {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return try {
            val date = sdf.parse(input)
            date?.time ?: 0
        } catch (e: Exception) {
            0
        }
    } else {
        0
    }
}