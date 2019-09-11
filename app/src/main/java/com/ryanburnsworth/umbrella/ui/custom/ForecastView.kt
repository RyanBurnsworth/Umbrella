package com.ryanburnsworth.umbrella.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.ryanburnsworth.umbrella.R
import com.ryanburnsworth.umbrella.data.APIServicesProvider
import kotlinx.android.synthetic.main.view_single_forecast.view.*

class ForecastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_single_forecast, this, true)
    }

    fun setTemp(temp: String) {
        this.forecast_view_temp.text =
            resources.getString(R.string.temp_value, temp)
    }

    @SuppressLint("SimpleDateFormat")
    fun setTime(time: String) {
        this.forecast_view_time.text = time
    }

    fun loadIcon(iconUrl: String, isHighlighted: Boolean = false) {
        Glide.with(this).load(APIServicesProvider().iconApi.getUrlForIcon(iconUrl, isHighlighted))
            .into(forecast_view_icon)
        if (!isHighlighted)
            setDefaultIconColor()
    }

    fun setIconHighlightColor(isWarm: Boolean) {
        if (isWarm) {
            forecast_view_icon.setColorFilter(resources.getColor(R.color.warmWeather))
            forecast_view_time.setTextColor(resources.getColor(R.color.warmWeather))
            forecast_view_temp.setTextColor(resources.getColor(R.color.warmWeather))
        } else {
            forecast_view_icon.setColorFilter(resources.getColor(R.color.coolWeather))
            forecast_view_time.setTextColor(resources.getColor(R.color.coolWeather))
            forecast_view_temp.setTextColor(resources.getColor(R.color.coolWeather))
        }
    }

    private fun setDefaultIconColor() {
        forecast_view_icon.setColorFilter(resources.getColor(android.R.color.black))
        forecast_view_time.setTextColor(resources.getColor(android.R.color.black))
        forecast_view_temp.setTextColor(resources.getColor(android.R.color.black))
    }
}
