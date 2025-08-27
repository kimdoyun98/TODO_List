package com.example.todo_list

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.project.data.local.preference.PreferenceUtil
import com.project.worker.ResetRoutineWorker
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
            ResetRoutineWorker.Companion.runReset(this)
            pref.setWorkerState(false)
        }
    }

    companion object {
        lateinit var instance: MyApplication
    }
}
