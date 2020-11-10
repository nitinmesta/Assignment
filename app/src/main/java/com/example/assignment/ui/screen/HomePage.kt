package com.example.assignment.ui.screen

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.WeatherApp
import com.example.assignment.data.model.*
import com.example.assignment.isNetworkAvailable
import com.example.assignment.ui.adapter.CityWeatherAdapter
import com.example.assignment.viewmodel.HomePageViewModel
import com.google.android.material.snackbar.Snackbar

class HomePage : AppCompatActivity() {
    var rootView: View? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: AppCompatTextView
    lateinit var cityWeatherAdapter: CityWeatherAdapter
    lateinit var viewModel: HomePageViewModel
    lateinit var progressLoader: RelativeLayout
    lateinit var progressText: AppCompatTextView
    lateinit var searchView: SearchView
    var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        initViewsAndObjects()
        loadSavedData()
        handleSearchQuery()
    }

    private fun loadSavedData() {
        showProgressLoader(getString(R.string.msg_loading_saved_data))
        viewModel.weatherLiveData.observe(this,
            {
                hideProgressLoader()
                showDataLayout(
                    if (it.isNotEmpty()) {
                        cityWeatherAdapter.setCityWeatherData(ArrayList(it),query)
                        true
                    } else {
                        false
                    }
                )
            })
    }

    private fun initViewsAndObjects() {
        rootView = findViewById(R.id.root_view)
        recyclerView = findViewById(R.id.weather_list)
        emptyView = findViewById(R.id.empty_view)
        progressLoader = findViewById(R.id.progress_layout)
        progressText = findViewById(R.id.progress_text)
        searchView = findViewById(R.id.search_field)
        //TODO get the view model from factory
        viewModel = HomePageViewModel((application as WeatherApp).weatherDatabase)
        cityWeatherAdapter = CityWeatherAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cityWeatherAdapter
    }

    private fun handleSearchQuery() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isNetworkAvailable(this@HomePage)) {
                    searchView.clearFocus()
                    recyclerView.scrollToPosition(0)
                    if (!query.isNullOrBlank()) {
                        this@HomePage.query = query
                        showProgressLoader(
                            String.format(
                                getString(R.string.msg_retriving_weather_data_for),
                                query
                            )
                        )
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
                } else {
                    showSnackBar(getString(R.string.err_no_internet))
                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                this@HomePage.query = newText
                return true
            }


        })

    }

    private fun hideProgressLoader() {
        progressLoader.visibility = View.GONE
    }

    private fun showProgressLoader(message: String) {
        progressLoader.visibility = View.VISIBLE
        progressText.text = message
    }

    private fun showGenericError() {
        hideProgressLoader()
        showSnackBar(String.format(getString(R.string.err_generic)))
    }

    private fun showErrorInfo(cityName: String) {
        hideProgressLoader()
        showSnackBar(String.format(getString(R.string.weather_data_not_found), cityName))
    }

    private fun showNewWeatherInfo(it: WeatherApiSuccessResponse<WeatherResponse>) {
        // store it in room db which can be accessed later
        //now lets create a mock of it
        showDataLayout(true)
        viewModel.storeNewData(it)

    }

    private fun showDataLayout(hasData: Boolean) {
        hideProgressLoader()
        recyclerView.visibility = if (hasData) View.VISIBLE else View.GONE
        emptyView.visibility = if (!hasData) View.VISIBLE else View.GONE
    }

    private fun showSnackBar(message: String) {
        rootView?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }
}