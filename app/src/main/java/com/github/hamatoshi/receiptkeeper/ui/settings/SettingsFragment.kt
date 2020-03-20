package com.github.hamatoshi.receiptkeeper.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.hamatoshi.receiptkeeper.R
import com.github.hamatoshi.receiptkeeper.data.ReceiptContentRepository
import com.github.hamatoshi.receiptkeeper.data.ReceiptDatabase
import com.github.hamatoshi.receiptkeeper.data.ReceiptSummaryRepository
import com.github.hamatoshi.receiptkeeper.util.SettingsUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val deleteAllPreference: Preference? = findPreference("deleteAll")
        deleteAllPreference?.setOnPreferenceClickListener {
            showDeleteDialog()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle("Delete all data")
            .setMessage("Are you sure to delete all data?")
            .setPositiveButton("YES") { dialog, _ ->
                deleteAllData()
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun deleteAllData() {
        val application = requireNotNull(this.activity).application
        val receiptDatabase = ReceiptDatabase.getInstance(application)
        val receiptSummaryRepository = ReceiptSummaryRepository(receiptDatabase.receiptSummaryDatabaseDao)
        val receiptContentRepository = ReceiptContentRepository(receiptDatabase.receiptContentDatabaseDao)
        val settingScope = CoroutineScope(Dispatchers.IO)
        settingScope.launch {
            receiptSummaryRepository.clear()
            receiptContentRepository.clear()
        }
    }
}
