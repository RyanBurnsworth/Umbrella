package com.ryanburnsworth.umbrella.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ryanburnsworth.umbrella.R
import com.ryanburnsworth.umbrella.data.APIServicesProvider
import com.ryanburnsworth.umbrella.data.model.TempUnit
import com.ryanburnsworth.umbrella.data.model.WeatherResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
