package com.example.rakuten.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.rakuten.R
import com.example.rakuten.data.WeatherApiErrorResponse
import com.example.rakuten.data.WeatherApiSuccessResponse
import com.example.rakuten.data.WeatherApiUnknownError
import com.example.rakuten.data.WeatherResponse
import com.example.rakuten.viewmodel.HomePageViewModel
import com.google.android.material.snackbar.Snackbar

class HomePage : AppCompatActivity() {
    var rootView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        rootView = findViewById(R.id.root_view)
        val viewModel = HomePageViewModel()
        val searchView = findViewById<SearchView>(R.id.search_field)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                viewModel.getWeatherData(query.toString()).observe(this@HomePage, {
                    when (it) {
                        is WeatherApiSuccessResponse -> showNewWeatherInfo(it)
                        is WeatherApiErrorResponse -> query?.let { it1 -> showErrorInfo(it1) }
                        is WeatherApiUnknownError -> showGenericError()
                        else -> showGenericError()
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun showGenericError() {

    }

    private fun showErrorInfo(cityName: String) {
        showSnackBar(String.format(getString(R.string.weather_data_not_found), cityName))
    }

    private fun showNewWeatherInfo(it: WeatherApiSuccessResponse<WeatherResponse>) {

    }

    private fun showSnackBar(message: String) {
        rootView?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }
}