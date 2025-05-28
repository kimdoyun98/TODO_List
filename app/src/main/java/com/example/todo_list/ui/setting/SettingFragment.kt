package com.example.todo_list.ui.setting

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.todo_list.R
import com.example.todo_list.util.MyApplication


class SettingFragment : PreferenceFragmentCompat() {
    private lateinit var prefs: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        if (rootKey == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            PUSH_ALERT -> {
                if (MyApplication.prefs.getAlarm()) MyApplication.prefs.setAlarm(false)
                else MyApplication.prefs.setAlarm(true)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        prefs.registerOnSharedPreferenceChangeListener(prefListener)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onPause() {
        super.onPause()
        prefs.unregisterOnSharedPreferenceChangeListener(prefListener)
    }

    companion object {
        private const val PUSH_ALERT = "pushAlert"
    }
}
