package com.project.ui

import com.project.data.local.room.entity.RoutineEntity
import com.project.data.local.room.entity.RoutineLog
import com.project.data.local.room.entity.StatisticsLog
import com.project.data.repository.log.RoutineLogRepository
import com.project.data.repository.log.StatisticsLogRepository
import java.time.LocalDate
import java.util.Calendar

suspend fun createRoutineLog(
    repository: RoutineLogRepository,
    todayRoutine: List<RoutineEntity>
) {
    repository.createLog(
        RoutineLog(
            date = LocalDate.now(),
            routines = todayRoutine.associateBy { it.id }
        )
    )
}

fun List<RoutineEntity>.filterTodayRoutine(): List<RoutineEntity> {
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return this.filter { it.day?.get(today - 1) ?: false }
}

fun isTodayRoutineLog(dateTime: LocalDate?): Boolean {
    if (dateTime == null) return false

    val today = LocalDate.now()
    return today.isEqual(dateTime)
}

suspend fun createLogStatisticsLog(
    statisticsLogRepository: StatisticsLogRepository,
    routineLog: RoutineLog
) {
    val values = routineLog.routines?.values

    statisticsLogRepository.createStatisticsLog(
        StatisticsLog(
            routineLogId = routineLog.id,
            total = values?.size ?: 0,
            success = values?.count { it.success ?: false } ?: 0
        )
    )
}
