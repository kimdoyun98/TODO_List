package com.example.todo_list.util

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.todo_list.worker.ResetRoutineWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
        instance = this

        if(prefs.getWorkerState()){
            ResetRoutineWorker.runReset(this)
            prefs.setWorkerState(false)
        }
    }

    companion object {
        lateinit var instance: MyApplication
        lateinit var prefs: PreferenceUtil
    }
}
