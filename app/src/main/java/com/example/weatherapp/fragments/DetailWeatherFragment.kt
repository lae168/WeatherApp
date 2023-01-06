//package com.example.weatherapp.fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.navArgs
//import com.example.weatherapp.MainActivity
//import com.example.weatherapp.R
//import com.example.weatherapp.vm.WeatherViewModel
//import kotlinx.android.synthetic.main.fragment_detail.*
//
//
//
//class DetailWeatherFragment : Fragment(R.layout.fragment_detail) {
//
//    lateinit var viewModel: WeatherViewModel
//    val args: DetailWeatherFragmentArgs by navArgs()
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = (activity as MainActivity).viewModel
//        val forecast = args.forecastday
//        forecast?.let { weather ->
//            tvDate.apply {
//                this.text=weather.date
//            }
//            tvWeather.apply {
//                this.text=weather.day.condition.toString()
//            }
//            tvTemp_c.apply {
//                this.text=weather.day.avgtemp_c.toString()
//            }
//            tvTemp_f.apply {
//                this.text=weather.day.avgtemp_f.toString()
//            }
//            tvPressure.apply {
//                this.text=weather.day.uv.toString()
//            }
//            tvHumidity.apply {
//                this.text=weather.day.avghumidity.toString()
//            }
//            tvWind.apply {
//                this.text=weather.day.maxwind_kph.toString()
//            }
//
//
//
//
////            tvDate.text=weather.date
////            tvWeather.text=weather.day.condition.toString()
////            tvTemp_c.text=weather.day.avgtemp_c.toString()
////            tvTemp_f.text=weather.day.avgtemp_f.toString()
////            tvUv.text=weather.day.uv.toString()
////            tvHumidity.text=weather.day.avghumidity.toString()
////            tvWind.text=weather.day.maxwind_kph.toString()
//            }
//        }
//    }
//
//
