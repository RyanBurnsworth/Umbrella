package com.ryanburnsworth.umbrella.data

import com.google.gson.GsonBuilder
import com.ryanburnsworth.umbrella.BuildConfig
import com.ryanburnsworth.umbrella.data.api.DateDeserializer
import com.ryanburnsworth.umbrella.data.api.IconAPI
import com.ryanburnsworth.umbrella.data.api.WeatherService
import com.ryanburnsworth.umbrella.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object APIServicesProvider {

    /**
     * Instance of ready to use [IconApi]
     */
    val iconApi = IconAPI()

    /**
     * Instance of the [WeatherService] service that is ready to use.
     */
    val weatherService: WeatherService

    /**
     * Instance of OkHttpClient for Retrofit to use
     */
    private var client: OkHttpClient? = null

    /**
     * Initialize Weather Service
     */
    init {
        createOKHttpClient()
        weatherService = retrofit()
            .create(WeatherService::class.java)
    }

    /**
     * Provide a Gson Builder than can deserialize the data from DarkSky's API
     */
    private fun provideGson() = GsonBuilder()
        .apply { registerTypeAdapter(Date::class.java, DateDeserializer()) }
        .create()

    /**
     * Instantiate the OkHttpClient
     */
    private fun createOKHttpClient() {
        client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    /**
     * Instantiate the Retrofit object
     */
    private fun retrofit() = Retrofit.Builder()
        .client(client ?: OkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()
}
