package com.example.todo_list.ui.home

import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.log.StatisticsLogRepository
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.data.room.RoutineLog
import com.example.todo_list.data.room.StatisticsLog
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
