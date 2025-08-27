package com.example.todo_list.util

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.todo_list.worker.ResetRoutineWorker
import com.project.data.local.preference.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var pref: PreferenceUtil

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (pref.getWorkerState()) {
            ResetRoutineWorker.runReset(this)
            pref.setWorkerState(false)
        }
    }

    companion object {
        lateinit var instance: MyApplication
    }
}
