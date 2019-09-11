package com.ryanburnsworth.umbrella.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryanburnsworth.umbrella.R
import com.ryanburnsworth.umbrella.data.api.ZipCodeService
import com.ryanburnsworth.umbrella.data.api.ZipLocation
import com.ryanburnsworth.umbrella.data.api.ZipLocationListener
import com.ryanburnsworth.umbrella.data.model.ForecastCondition
import com.ryanburnsworth.umbrella.data.model.TempUnit
import com.ryanburnsworth.umbrella.ui.adapter.ForecastAdapter
import com.ryanburnsworth.umbrella.ui.viewmodel.MainActivityViewModel
import com.ryanburnsworth.umbrella.util.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ZipLocationListener {
    private val viewModel by viewModel<MainActivityViewModel>()
    private val preferenceManager: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeWeatherResponse()
    }

    override fun onResume() {
        super.onResume()
        ZipCodeService.getLatLongByZip(this, preferenceManager.getZipCode() ?: "", this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.settings_menu -> {
                startActivity<SettingsActivity>()
            }
        }
        return true
    }

    private fun observeWeatherResponse() {
        viewModel.currentForecast.observe(this, Observer {
            header_current_temp?.text = getString(R.string.temp_value, it.temp.toInt().toString())
            header_current_summary?.text = it.summary
        })

        viewModel.hourlyForecast.observe(this, Observer {
            initializeRecyclerView(it)
        })

        viewModel.errorHandler.observe(this, Observer {
            toast(it)
            startActivity<SettingsActivity>()
        })
    }

    private fun initializeRecyclerView(
        hourlyForecast: ArrayList<ArrayList<ForecastCondition>>
    ) {
        val adapter = ForecastAdapter(hourlyForecast)
        weather_recycler_view.adapter = adapter
        weather_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onLocationFound(location: ZipLocation) {
        supportActionBar?.title = location.city + ", " + location.state
        viewModel.listenForChanges(location.longitude, location.latitude, TempUnit.FAHRENHEIT)
    }

    override fun onLocationNotFound() {
        toast(R.string.zip_code_not_found)
        startActivity<SettingsActivity>()
    }
}
