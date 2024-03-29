package com.ryanburnsworth.umbrella.data.model

import com.google.gson.annotations.SerializedName

/**
 * Temperature unit to be used in requests for [com.nerdery.umbrella.data.api.WeatherService]
 */
enum class TempUnit(private val value: String) {
    @SerializedName("si")
    CELSIUS("si"),

    @SerializedName("us")
    FAHRENHEIT("us");

    override fun toString(): String {
        return value
    }

    companion object {
        fun stringToTempUnit(string: String): TempUnit {
            when (string) {
                "Fahrenheit" -> return FAHRENHEIT
                "Celsius" -> return CELSIUS
            }
            return FAHRENHEIT
        }
    }
}
