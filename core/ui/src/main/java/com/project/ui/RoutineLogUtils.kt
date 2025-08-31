package com.project.ui

import com.project.data.repository.log.RoutineLogRepository
import com.project.data.repository.log.StatisticsLogRepository
import com.project.model.Routine
import com.project.model.RoutineLog
import com.project.model.StatisticsLog
import java.time.LocalDate
import java.util.Calendar

suspend fun createRoutineLog(
    repository: RoutineLogRepository,
    todayRoutine: List<Routine>
) {
    repository.createLog(
        RoutineLog(
            date = LocalDate.now(),
            routines = todayRoutine.associateBy { it.id }
        )
    )
}

fun List<Routine>.filterTodayRoutine(): List<Routine> {
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return this.filter { it.day[today - 1] }
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
