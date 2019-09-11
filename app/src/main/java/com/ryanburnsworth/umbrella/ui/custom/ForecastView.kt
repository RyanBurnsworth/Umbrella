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
import java.text.SimpleDateFormat
import java.util.*

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

    fun loadIcon(iconUrl: String) {
        Glide.with(this).load(APIServicesProvider().iconApi.getUrlForIcon(iconUrl, false))
            .into(forecast_view_icon)
    }
}
