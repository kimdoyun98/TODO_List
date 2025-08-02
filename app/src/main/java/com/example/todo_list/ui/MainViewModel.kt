package com.example.todo_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.log.StatisticsLogRepository
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.data.room.RoutineLog
import com.example.todo_list.ui.home.utils.createLogStatisticsLog
import com.example.todo_list.ui.home.utils.filterTodayRoutine
import com.example.todo_list.ui.home.utils.isTodayRoutineLog
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
class MainViewModel @Inject constructor(
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

    private fun checkRoutineLog(todayRoutine: List<RoutineEntity>) =
        routineLogRepository.getTodayLog()
            .onEach { _todayRoutineLog.value = it }
            .filter {
                it == null || !isTodayRoutineLog(it.date)
            }
            .onEach {
                it?.let { createLogStatisticsLog(statisticsLogRepository, it) }
                com.example.todo_list.ui.home.utils.createRoutineLog(
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

    private fun updateRoutineLog(todayRoutine: List<RoutineEntity>) =
        todayRoutineLog
            .filterNotNull()
            .filter { isTodayRoutineLog(it.date) && todayRoutine.size != it.routines!!.size }
            .onEach { routineLog ->
                val newRoutinesMap: Map<Int, RoutineEntity>

                if (todayRoutine.size > routineLog.routines!!.size) {
                    newRoutinesMap = routineLog.routines.toMutableMap()
                    todayRoutine.forEach { entity ->
                        newRoutinesMap.getOrPut(entity.id) { entity }
                    }
                } else {
                    newRoutinesMap = mutableMapOf()
                    todayRoutine.forEach { todayRoutine ->
                        newRoutinesMap.getOrPut(todayRoutine.id) { routineLog.routines[todayRoutine.id]!! }
                    }
                }

                routineLogRepository.update(
                    routineLog.copy(routines = newRoutinesMap)
                )
            }
}
