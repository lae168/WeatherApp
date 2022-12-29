package com.example.weatherapp.data

data class ForecastWeatherResponse(

    val current: Current,

    val forecast: Forecast,

    val location: Location
)