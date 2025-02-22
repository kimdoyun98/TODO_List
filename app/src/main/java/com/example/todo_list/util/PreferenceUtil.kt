package com.example.todo_list.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

    fun getAlarm(): Boolean {
        return prefs.getBoolean(PUSH_ALARM, false)
    }

    fun setAlarm(value: Boolean) {
        prefs.edit().putBoolean(PUSH_ALARM, value).apply()
    }

    fun getWorkerState(): Boolean {
        return prefs.getBoolean(MIDNIGHT_RESET, true)
    }

    fun setWorkerState(value: Boolean) {
        prefs.edit().putBoolean(MIDNIGHT_RESET, value).apply()
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val MIDNIGHT_RESET = "midnight_reset"
        private const val PUSH_ALARM = "pushAlarm"
    }
}
