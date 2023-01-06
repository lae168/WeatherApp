package com.example.weatherapp.api

import com.example.weatherapp.data.ForecastWeatherResponse
import com.example.weatherapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") location : String ,
        @Query("key")  apiKey: String = API_KEY
    ) : Response<ForecastWeatherResponse>


    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") location : String = "Canada",
        @Query("key")  apiKey: String = API_KEY,
        @Query("days") days : String = "14"
    ) : Response<ForecastWeatherResponse>


}