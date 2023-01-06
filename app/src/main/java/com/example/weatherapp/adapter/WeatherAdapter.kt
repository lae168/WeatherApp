package com.androiddevs.mvvmnewsapp.prj.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.Forecastday
import kotlinx.android.synthetic.main.item_forecast_preview.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallBack = object : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forecast_preview, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = differ.currentList[position]
        holder.itemView.apply {
//            Glide.with(this).load(weather.day.condition.icon).into(ivWeatherIcon)
            tvHumidity.text = "Humidity-" + weather.day.avghumidity.toString()
            tvDate.text = "Date-" + weather.date
            tvTemp.text = "Temp-(" + weather.day.avgtemp_c.toString() + "C)"
            tvUv.text = "UV-" + weather.day.uv.toString()
            tvWindSpeed.text = "Wind-" + weather.day.maxwind_kph + "kph"

        }
    }
    override fun getItemCount(): Int {
            return differ.currentList.size
        }
}