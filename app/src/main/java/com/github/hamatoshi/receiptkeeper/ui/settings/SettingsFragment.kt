package com.github.hamatoshi.receiptkeeper.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.github.hamatoshi.receiptkeeper.R
import com.github.hamatoshi.receiptkeeper.util.SettingsUtils

class SettingsFragment : PreferenceFragmentCompat() {

    val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
            if (key == "theme") {
                val theme = preferences.getString("theme", "")?.toInt()
                SettingsUtils.applyTheme(theme)
            }
        }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
