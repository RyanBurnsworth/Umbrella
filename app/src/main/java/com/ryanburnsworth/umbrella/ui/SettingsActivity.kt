package com.ryanburnsworth.umbrella.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ryanburnsworth.umbrella.R
import com.ryanburnsworth.umbrella.util.TEMP_UNIT
import com.ryanburnsworth.umbrella.util.ZIP_CODE

private const val TITLE_TAG = "Settings"

class SettingsActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        } else {
            title = savedInstanceState.getCharSequence(TITLE_TAG)
        }
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                setTitle(R.string.settings_title)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.lt_grey)))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, title)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        // Instantiate the new Fragment
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            pref.fragment
        ).apply {
            arguments = args
            setTargetFragment(caller, 0)
        }
        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings, fragment)
            .addToBackStack(null)
            .commit()
        title = pref.title
        return true
    }

    /**
     * The preference fragment displays the users zip code and temperature unit preferences
     */
    class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            val zipPref = findPreference<Preference>(ZIP_CODE)
            val tempUnitPref = findPreference<Preference>(TEMP_UNIT)

            zipPref.let { it?.setOnPreferenceChangeListener(this) }
            tempUnitPref.let { it?.setOnPreferenceChangeListener(this) }

            zipPref.let {
                it?.summary = preferenceManager.sharedPreferences.getString(
                    ZIP_CODE,
                    ""
                )
            }

            tempUnitPref.let {
                it?.summary = preferenceManager.sharedPreferences.getString(
                    TEMP_UNIT,
                    resources.getString(R.string.default_temp_unit)
                )
            }
        }

        /**
         * Update the UI when a user changes their preference
         */
        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            when (preference?.key ?: "") {
                ZIP_CODE -> preference.let { it?.summary = newValue as String }
                TEMP_UNIT -> preference.let { it?.summary = newValue as String }
            }
            return true
        }
    }
}
