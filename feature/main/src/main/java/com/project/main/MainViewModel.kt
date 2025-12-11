package com.project.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.repository.log.RoutineLogRepository
import com.project.data.repository.log.StatisticsLogRepository
import com.project.data.repository.routine.RoutineRepository
import com.project.model.Routine
import com.project.model.RoutineLog
import com.project.ui.createLogStatisticsLog
import com.project.ui.filterTodayRoutine
import com.project.ui.isTodayRoutineLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
    private val statisticsLogRepository: StatisticsLogRepository,
) : ViewModel() {
    private val _todayRoutineLog = MutableStateFlow<RoutineLog?>(null)
    private val todayRoutineLog = _todayRoutineLog.asStateFlow()

    init {
        createRoutineLog()
        updateRoutineLog()
    }

    private fun createRoutineLog() {
        routineRepository.selectAll()
            .map { it.filterTodayRoutine() }
            .flatMapLatest {
                checkRoutineLog(it)
            }
            .launchIn(viewModelScope)
    }

    private fun checkRoutineLog(todayRoutine: List<Routine>) =
        routineLogRepository.getTodayLog()
            .onEach { _todayRoutineLog.value = it }
            .filter {
                it == null || !isTodayRoutineLog(it.date)
            }
            .onEach {
                it?.let { createLogStatisticsLog(statisticsLogRepository, it) }
                com.project.ui.createRoutineLog(
                    routineLogRepository,
                    todayRoutine
                )
            }

    private fun updateRoutineLog() {
        routineRepository.selectAll()
            .map { it.filterTodayRoutine() }
            .flatMapLatest {
                updateRoutineLog(it)
            }
            .launchIn(viewModelScope)
    }

    private fun updateRoutineLog(todayRoutine: List<Routine>) =
        todayRoutineLog
            .filterNotNull()
            .filter { isTodayRoutineLog(it.date) && todayRoutine.size != it.routines!!.size }
            .onEach { routineLog ->
                val newRoutinesMap: Map<Int, Routine>

                if (todayRoutine.size > routineLog.routines!!.size) {
                    newRoutinesMap = routineLog.routines!!.toMutableMap()
                    todayRoutine.forEach { entity ->
                        newRoutinesMap.getOrPut(entity.id) { entity }
                    }
                } else {
                    newRoutinesMap = mutableMapOf()
                    todayRoutine.forEach { todayRoutine ->
                        newRoutinesMap.getOrPut(todayRoutine.id) { routineLog.routines!![todayRoutine.id]!! }
                    }
                }

                routineLogRepository.update(
                    routineLog.copy(routines = newRoutinesMap)
                )
            }
}
