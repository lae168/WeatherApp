package com.example.weatherapp.repository

import com.example.weatherapp.api.RetrofitInstance

class WeatherRepository()
 {
     suspend fun getCurrentWeather(location : String) =
        RetrofitInstance.api.getCurrentWeather(location)

    suspend fun getForecastWeather(location: String,apikey: String,days : String) =
        RetrofitInstance.api.getForecastWeather(location,apikey,days)
 }