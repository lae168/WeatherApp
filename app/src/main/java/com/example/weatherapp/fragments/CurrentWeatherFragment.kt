
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.util.Resource
import com.example.weatherapp.vm.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_current_weather.*


class CurrentWeatherFragment() : Fragment(R.layout.fragment_current_weather) {

    private lateinit var viewModel: WeatherViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        viewModel.currentWeather.observe(viewLifecycleOwner){ response->
            when(response){
                is Resource.Success ->{
                        response.data?.let { currentResponse ->
                        imgCurrent.setImageResource(R.drawable.weather)
                        city.text = currentResponse.location.country.toString()
                        coordinates.text = "Lon("+currentResponse.location.lon.toString()+") ,Lat("+
                                currentResponse.location.lat.toString()+")"
                        weather.text = currentResponse.current.condition.text
                        temp_c.text = currentResponse.current.temp_c.toString()+"°C"
                        temp_f.text = currentResponse.current.temp_f.toString()+"°F"
                        pressure.text = currentResponse.current.pressure_in.toString()+" mmHg"
                        humidity.text = currentResponse.current.humidity.toString()
                        wind.text = currentResponse.current.wind_kph.toString()+"kph"
                        degree.text =currentResponse.current.wind_degree.toString()+"°"
                        }
                }
                is Error ->{
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}
