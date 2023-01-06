package com.example.weatherapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.prj.adapters.WeatherAdapter
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.util.Resource
import com.example.weatherapp.vm.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_forecast_weather.*

class ForecastWeatherFragment : Fragment(R.layout.fragment_forecast_weather) {

    lateinit var viewModel: WeatherViewModel
    lateinit var weatherAdapter: WeatherAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()

//        weatherAdapter.setOnItemClickListener {
//            Log.d("TAG", "onViewCreated: $it")
//            val bundle = Bundle().apply {
//                putSerializable("forecastday", it)
//            }
//            findNavController().navigate(
//                R.id.action_forecast_weather_fragment_to_detailWeatherFragment,
//                bundle
//            )
//
//        }

        viewModel.forecastWeather.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let { forecastResponse ->
//                    viewModel.getForecastWeather("Canada", API_KEY, "14")
                        rvForecastWeather.apply {
                            weatherAdapter.differ.submitList(forecastResponse.forecast.forecastday)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressbar()
                }

            }
        }
    }

    private fun hideProgressbar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressbar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        weatherAdapter = WeatherAdapter()
        rvForecastWeather.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}