package com.ryanburnsworth.umbrella.data.api

import com.ryanburnsworth.umbrella.BuildConfig
import com.ryanburnsworth.umbrella.data.model.TempUnit
import com.ryanburnsworth.umbrella.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    /**
     * Retrieves the local forecast for a given long/lat
     * @param latitude - latitude of the zip code provided
     * @param longitude - longitude of the zip code provided
     * @param units - Farenheit or Celsius
     * @return a WeatherResponse object
     */
    @GET("/forecast/${BuildConfig.API_KEY}/{latitude},{longitude}")
    fun getWeatherCall(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("units") units: TempUnit
    ): Call<WeatherResponse>
}
