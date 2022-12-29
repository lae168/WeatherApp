package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.vm.WeatherViewModel
import com.example.weatherapp.vm.WeatherViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
      lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherRepository = WeatherRepository()
        val viewModelProviderFactory = WeatherViewModelProviderFactory(application,weatherRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(WeatherViewModel::class.java)
        bottomNavigationView.setupWithNavController(weatherNavHostFragment.findNavController())
    }
}