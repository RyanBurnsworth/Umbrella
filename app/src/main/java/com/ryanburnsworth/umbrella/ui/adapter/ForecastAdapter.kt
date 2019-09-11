package com.ryanburnsworth.umbrella.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryanburnsworth.umbrella.R
import com.ryanburnsworth.umbrella.data.model.ForecastCondition
import com.ryanburnsworth.umbrella.ui.custom.ForecastView
import com.ryanburnsworth.umbrella.util.DAY_COUNT
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapter(
    private val forecastList: ArrayList<ArrayList<ForecastCondition>>,
    private val tempIndices: Array<Int>
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forecast,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(forecastList[position], position, tempIndices)

    override fun getItemCount() = DAY_COUNT

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            forecast: List<ForecastCondition>,
            position: Int,
            tempIndices: Array<Int>
        ) = with(itemView) {

            val gridLayout = forecast_grid
            val forecastDateTv = itemView.findViewById<TextView>(R.id.forecast_response_date)

            if (position == 0) forecastDateTv.text =
                context.getString(R.string.today) else forecastDateTv.text = context.getString(
                R.string.tomorrow
            )

            for (fc in forecast.indices) {
                val forecastView = ForecastView(itemView.context)
                forecastView.setTemp(forecast[fc].formattedTemp)
                forecastView.setTime(forecast[fc].formattedTime)
                forecastView.loadIcon(forecast[fc].icon.toString())

                // if the forecast index matches the high or low temp index set the icon highlight
                if (position == 0) {
                    when (fc) {
                        tempIndices[0] -> {
                            forecastView.loadIcon(forecast[fc].icon.toString(), true)
                            forecastView.setIconHighlightColor(true)
                        }
                        tempIndices[1] -> {
                            forecastView.loadIcon(forecast[fc].icon.toString(), true)
                            forecastView.setIconHighlightColor(false)
                        }
                    }
                }

                // if the forecast index matches the high or low temp index set the icon highlight
                if (position == 1) {
                    when (fc) {
                        tempIndices[2] -> {
                            forecastView.loadIcon(forecast[fc].icon.toString(), true)
                            forecastView.setIconHighlightColor(true)
                        }
                        tempIndices[3] -> {
                            forecastView.loadIcon(forecast[fc].icon.toString(), true)
                            forecastView.setIconHighlightColor(false)
                        }
                    }
                }
                gridLayout.addView(forecastView)
            }
        }
    }
}
