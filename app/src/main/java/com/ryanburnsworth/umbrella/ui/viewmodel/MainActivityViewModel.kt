package com.ryanburnsworth.umbrella.ui.viewmodel

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryanburnsworth.umbrella.data.APIServicesProvider
import com.ryanburnsworth.umbrella.data.model.ForecastCondition
import com.ryanburnsworth.umbrella.data.model.HourlyResponse
import com.ryanburnsworth.umbrella.data.model.TempUnit
import com.ryanburnsworth.umbrella.data.model.WeatherResponse
import com.ryanburnsworth.umbrella.util.MAX_HOURS_PER_DAY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.roundToInt

class MainActivityViewModel(private val apiService: APIServicesProvider) : ViewModel() {
    val currentForecast = MutableLiveData<ForecastCondition>()
    val hourlyForecast = MutableLiveData<ArrayList<ArrayList<ForecastCondition>>>()
    val errorHandler = MutableLiveData<String>()

    /**
     * Updates current forecast and hourly forecast MutableLiveData objects
     * @param lng - user chosen longitude
     * @param lat - user chosen latitude
     * @param unit - user chosen temperature unit
     */
    fun listenForChanges(lng: Double, lat: Double, unit: TempUnit) {
        GlobalScope.launch(Dispatchers.IO) {
            apiService.weatherService.getWeatherCall(lng, lat, unit).enqueue(object :
                Callback<WeatherResponse> {
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    errorHandler.postValue("Error retrieving local weather. Please ensure your zip code is correct")
                    Log.e("WeatherService", "Error: ${t.localizedMessage}")
                }

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    val forecast = response.body()?.currentForecast
                    forecast?.temp = abs(forecast?.temp?.roundToInt()?.toDouble() ?: 0.0)
                    currentForecast.postValue(response.body()?.currentForecast)

                    val hourlyForecastList =
                        reformatHourlyForecastList(response.body()?.hourly)

                    hourlyForecast.postValue(hourlyForecastList)
                }
            })
        }
    }

    /**
     * @param hourlyResponse - An hourly response object from the server result
     * @return an arraylist containing hourly forecast conditions for today and tomorrow
     */
    @SuppressLint("SimpleDateFormat")
    private fun reformatHourlyForecastList(hourlyResponse: HourlyResponse?): ArrayList<ArrayList<ForecastCondition>> {
        val hourlyForecastList = arrayListOf<ArrayList<ForecastCondition>>()
        val hourlyForecastToday = arrayListOf<ForecastCondition>()
        val hourlyForecastTomorrow = arrayListOf<ForecastCondition>()

        var hourCounter = 0

        hourlyResponse?.hours?.forEach {
            // convert temp to a rounded integer string
            it.formattedTemp = abs(it.temp.roundToInt().toDouble()).toInt().toString()

            // convert time to a user friendly string
            it.formattedTime =
                setFormattedDate(SimpleDateFormat("HH:mm").format(it.time ?: Date(0L)))

            // separate forecasts for today and tomorrow
            if (DateUtils.isToday(it.time?.time ?: 0L)) {
                hourlyForecastToday.add(it)
            } else {
                // only allow up to 24 hours of forecasts for tomorrow
                if (hourCounter < MAX_HOURS_PER_DAY) {
                    hourlyForecastTomorrow.add(it)
                    hourCounter++
                }
            }
        }

        hourlyForecastList.add(hourlyForecastToday)
        hourlyForecastList.add(hourlyForecastTomorrow)

        return hourlyForecastList
    }

    /**
     * Converts a SimpleDateFormat string into a readable timestamp
     * @param date - date string using 24 hour time
     */
    private fun setFormattedDate(date: String): String {
        val hour = date.substring(0, 2).toInt()
        return when {
            hour > 12 -> (hour - 12).toString() + date.substring(2) + " PM"
            hour == 12 -> "$date PM"
            hour == 11 -> "$date AM"
            hour == 10 -> "$date AM"
            hour == 0 -> "12" + date.substring(2) + " AM"
            else -> date.substring(1) + " AM"
        }
    }
}
