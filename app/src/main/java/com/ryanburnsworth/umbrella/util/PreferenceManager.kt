package com.ryanburnsworth.umbrella.util

import android.content.Context
import org.jetbrains.anko.defaultSharedPreferences

class PreferenceManager(private val context: Context) {

    fun getZipCode() = context.defaultSharedPreferences.getString(ZIP_CODE, "Celsius")

    fun getTempUnits() = context.defaultSharedPreferences.getString(TEMP_UNIT, "Fahrenheit")
}