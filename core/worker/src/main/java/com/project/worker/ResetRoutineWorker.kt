package com.project.worker

import android.app.ForegroundServiceStartNotAllowedException
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.project.alarm.Alarm
import com.project.data.repository.log.RoutineLogRepository
import com.project.data.repository.log.StatisticsLogRepository
import com.project.data.repository.routine.RoutineRepository
import com.project.ui.createLogStatisticsLog
import com.project.ui.createRoutineLog
import com.project.ui.filterTodayRoutine
import com.project.ui.isTodayRoutineLog
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import com.project.core.ui.R
import com.project.model.Routine

@HiltWorker
class ResetRoutineWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val routineRepository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
    private val statisticsLogRepository: StatisticsLogRepository,
    private val alarm: Alarm
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val HOUR = 0
        private const val MINUTE = 0
        private const val WORKER_NAME = "RESET_ROUTINE_SUCCESS"
        private const val NOTIFICATION_ID = 101

        fun runReset(context: Context) {
            val duration = getDurationTime()

            val workRequest = OneTimeWorkRequestBuilder<ResetRoutineWorker>()
                .setInitialDelay(duration.seconds, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    WORKER_NAME,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,
                    workRequest
                )
        }

        private fun getDurationTime(): Duration {
            val newSyncTime = LocalTime.of(HOUR, MINUTE)
            val now: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
            val nowTime: LocalTime = now.toLocalTime()

            val plusDay = if (nowTime == newSyncTime || nowTime.isAfter(newSyncTime)) 1 else 0

            val nextTriggerTime = now.plusDays(plusDay.toLong())
                .withHour(newSyncTime.hour)
                .withMinute(newSyncTime.minute)

            return Duration.between(LocalDateTime.now(), nextTriggerTime)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun doWork(): Result = coroutineScope {
        try {
            setForeground(createForegroundInfo())

            routineLogRepository.getTodayLog()
                .filter { it == null || !isTodayRoutineLog(it.date) }
                .onEach {
                    it?.let { createLogStatisticsLog(statisticsLogRepository, it) }
                }
                .flatMapLatest {
                    routineRepository.selectAll()
                }
                .map { it.filterTodayRoutine() }
                .onEach { todayRoutine ->
                    createRoutineLog(routineLogRepository, todayRoutine)

                    todayRoutine.forEach { routine ->
                        setTodayAlarm(routine)
                    }
                }.launchIn(this)

            Result.success()
        } catch (e: ForegroundServiceStartNotAllowedException) {
            Result.success()
        } catch (e: Exception) {
            Log.e("DoWork Fail", "${e.message}")
            Result.failure()
        } finally {
            runReset(applicationContext)
        }
    }

    private fun setTodayAlarm(routine: Routine) {
        val (hour, min) = routine.time.split(":").map { it.toInt() }
        alarm.setAlarm(
            hour = hour,
            minute = min,
            alarm_code = routine.id,
            content = routine.title
        )
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, WORKER_NAME)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setTicker(applicationContext.getString(R.string.notification_reset_ticker)) //간단한 설명
            .setContentText(applicationContext.getString(R.string.notification_reset_content))
            .setSmallIcon(R.mipmap.todo_icon)
            .setOngoing(true)
            .setAutoCancel(false)
            .setShowWhen(true)
            .build()

        createChannel()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ForegroundInfo(NOTIFICATION_ID, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }

    private fun createChannel() {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        notificationManager.createNotificationChannel(
            NotificationChannel(WORKER_NAME, WORKER_NAME, importance)
        )
    }
}
