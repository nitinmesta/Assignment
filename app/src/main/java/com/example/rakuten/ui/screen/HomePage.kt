package com.example.rakuten.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rakuten.R
import com.example.rakuten.WeatherApp
import com.example.rakuten.data.model.*
import com.example.rakuten.ui.adapter.CityWeatherAdapter
import com.example.rakuten.viewmodel.HomePageViewModel
import com.google.android.material.snackbar.Snackbar

class HomePage : AppCompatActivity() {
    var rootView: View? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: AppCompatTextView
    lateinit var cityWeatherAdapter: CityWeatherAdapter
    lateinit var viewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        rootView = findViewById(R.id.root_view)
        recyclerView = findViewById(R.id.weather_list)
        emptyView = findViewById(R.id.empty_view)
        //TODO get the view model from factory
        viewModel = HomePageViewModel((application as WeatherApp).weatherDatabase)
        cityWeatherAdapter = CityWeatherAdapter()
        viewModel.weatherLiveData.observe(this,
            {
                showDataLayout(
                    if (it.isNotEmpty()) {
                        cityWeatherAdapter.setCityWeatherData(ArrayList(it))
                        true
                    } else {
                        false
                    }
                )
            })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cityWeatherAdapter


        val searchView = findViewById<SearchView>(R.id.search_field)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if (!query.isNullOrBlank()) {
                    viewModel.getWeatherData(query.trim()).observe(this@HomePage, {
                        when (it) {
                            is WeatherApiSuccessResponse -> showNewWeatherInfo(it)
                            is WeatherApiErrorResponse -> showErrorInfo(query)
                            is WeatherApiUnknownError -> showGenericError()
                            else -> showGenericError()
                        }
                    })
                } else {
                    showSnackBar(getString(R.string.err_enter_city))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun showGenericError() {
        showSnackBar(String.format(getString(R.string.err_generic)))
    }

    private fun showErrorInfo(cityName: String) {
        showSnackBar(String.format(getString(R.string.weather_data_not_found), cityName))
    }

    private fun showNewWeatherInfo(it: WeatherApiSuccessResponse<WeatherResponse>) {
        // store it in room db which can be accessed later
        //now lets create a mock of it
        showDataLayout(true)
        viewModel.storeNewData(it)

    }

    private fun showDataLayout(hasData: Boolean) {
        recyclerView.visibility = if (hasData) View.VISIBLE else View.GONE
        emptyView.visibility = if (!hasData) View.VISIBLE else View.GONE
    }

    private fun showSnackBar(message: String) {
        rootView?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }
}