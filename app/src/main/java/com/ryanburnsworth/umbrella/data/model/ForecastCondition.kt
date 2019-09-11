package com.ryanburnsworth.umbrella.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Specific weather condition for time and location
 */
data class ForecastCondition(
    /**
     * Text summary of weather condition
     * @return Summary
     */
    @SerializedName("summary") var summary: String?,
    /**
     * Icon name of weather condition
     * @return Icon Name
     */
    @SerializedName("icon") var icon: String?,
    /**
     * Temperature in degrees of [TempUnit] sent during request
     * @return Temperature
     */
    @SerializedName("temperature") var temp: Double = 0.0,
    /**
     * Time/Date of Forecast Condition
     * @return Date
     */
    @SerializedName("time") var time: Date?,

    /**
     * returns a temperature as a string
     */
    var formattedTemp: String = "",

    /**
     * returns a formatted time (Example: 7:00PM)
     */
    var formattedTime: String = ""
)
