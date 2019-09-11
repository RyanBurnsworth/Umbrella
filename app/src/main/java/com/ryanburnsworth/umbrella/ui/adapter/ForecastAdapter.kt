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

private const val DAY_ONE_HIGH = 0
private const val DAY_ONE_LOW = 1
private const val DAY_TWO_HIGH = 2
private const val DAY_TWO_LOW = 3

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

            // today = 0 and tomorrow = 1
            if (position == 0) forecastDateTv.text =
                context.getString(R.string.today) else forecastDateTv.text = context.getString(
                R.string.tomorrow
            )

            for (i in forecast.indices) {
                val forecastView = ForecastView(itemView.context)
                forecastView.setTemp(forecast[i].formattedTemp)
                forecastView.setTime(forecast[i].formattedTime)
                forecastView.loadIcon(forecast[i].icon.toString())

                // if the forecast index matches the high or low temp index set the icon highlight
                if (position == 0 && tempIndices.size == 4) {
                    when (i) {
                        tempIndices[DAY_ONE_HIGH] -> {
                            forecastView.loadIcon(forecast[i].icon.toString(), true)
                            forecastView.setHighlightColor(true)
                        }
                        tempIndices[DAY_ONE_LOW] -> {
                            forecastView.loadIcon(forecast[i].icon.toString(), true)
                            forecastView.setHighlightColor(false)
                        }
                    }
                }

                // if the forecast index matches the high or low temp index set the icon highlight
                if (position == 1 && tempIndices.size == 4) {
                    when (i) {
                        tempIndices[DAY_TWO_HIGH] -> {
                            forecastView.loadIcon(forecast[i].icon.toString(), true)
                            forecastView.setHighlightColor(true)
                        }
                        tempIndices[DAY_TWO_LOW] -> {
                            forecastView.loadIcon(forecast[i].icon.toString(), true)
                            forecastView.setHighlightColor(false)
                        }
                    }
                }
                gridLayout.addView(forecastView)
            }
        }
    }
}
