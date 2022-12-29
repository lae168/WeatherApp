package com.example.weatherapp.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.MainApplication
import com.example.weatherapp.data.ForecastWeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.util.Constants
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class WeatherViewModel(app: Application, val weatherRepository: WeatherRepository) :
    AndroidViewModel(app) {

    val forecastWeather: MutableLiveData<Resource<com.example.weatherapp.data.ForecastWeatherResponse>> = MutableLiveData()

    var forecastWeatherResponse: com.example.weatherapp.data.ForecastWeatherResponse? = null
    var currentWeatherResponse: ForecastWeatherResponse? = null

    val currentWeather: MutableLiveData<Resource<com.example.weatherapp.data.ForecastWeatherResponse>> = MutableLiveData()

    fun getForecastWeather(location: String, apikey: String, days: String) = viewModelScope.launch {
        safeForecastWeatherCall(location, apikey, days)
    }

    fun getCurrentWeather(location: String) = viewModelScope.launch {
        safeCurrentWeatherCall(location)
    }

    init {
        getCurrentWeather("Canada")
        getForecastWeather("Canada", Constants.API_KEY, "14")

    }

    private fun handleForecastWeatherResponse(
        response: Response<com.example.weatherapp.data.ForecastWeatherResponse>
    )
            : Resource<com.example.weatherapp.data.ForecastWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (forecastWeatherResponse == null) {
                    forecastWeatherResponse = resultResponse
                } else {
                    var oldArticles = forecastWeatherResponse?.forecast?.forecastday
                    var newArticles = resultResponse.forecast.forecastday
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(forecastWeatherResponse ?: resultResponse)
            }

        }
        return Resource.Error(response.message())

    }

    private suspend fun safeForecastWeatherCall(location: String, apikey: String, days: String) {
        forecastWeather.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = weatherRepository.getForecastWeather(location, apikey, days)
                forecastWeather.postValue(handleForecastWeatherResponse(response))
            } else {
                forecastWeather.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> forecastWeather.postValue(Resource.Error("Network Failure"))
                else -> forecastWeather.postValue(Resource.Error("Conversion Error"))
            }

        }
    }
    private fun handleCurrentWeatherResponse(
        response: Response<com.example.weatherapp.data.ForecastWeatherResponse>
    )
            : Resource<com.example.weatherapp.data.ForecastWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (currentWeatherResponse == null) {
                    currentWeatherResponse = resultResponse
                } else {
                    var oldArticles = currentWeatherResponse?.current
                    var newArticles = resultResponse.current
                    oldArticles=newArticles

                }
                return Resource.Success(currentWeatherResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeCurrentWeatherCall(location: String) {
        try {
            if (hasInternetConnection()) {
                val response = weatherRepository.getCurrentWeather(location)
                currentWeather.postValue(handleCurrentWeatherResponse(response))
            } else {
                currentWeather.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> currentWeather.postValue(Resource.Error("Network Failure"))
                else -> currentWeather.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
//        check that the user has connection or not
        val connectivityManager = getApplication<MainApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
//            Get the NetworkCapabilities for the given Network. This will return null if the network is unknown.
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
//                Tests for the presence of a transport on this instance.
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false

                }
            }
        }
        return false
    }
}




