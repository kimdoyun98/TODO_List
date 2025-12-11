package com.project.setting

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.project.data.local.preference.PreferenceUtil
import com.project.feature.setting.R
import javax.inject.Inject

internal class SettingFragment : PreferenceFragmentCompat() {
    private lateinit var prefs: SharedPreferences
    private var alarmSwitch: SwitchPreference? = null

    @Inject
    lateinit var prefUtil: PreferenceUtil

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        if (rootKey == null) {
            alarmSwitch = findPreference(PUSH_ALERT)
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            PUSH_ALERT -> {
                prefUtil.setAlarm(alarmSwitch!!.isChecked)
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
