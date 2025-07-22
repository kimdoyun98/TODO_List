package com.example.todo_list.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.RoutineEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val routineRepository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
) : ViewModel() {

    val todayRoutine = routineLogRepository.getTodayLog()
        .map { it?.routines?.values?.toList() ?: emptyList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    val getScheduleAll = scheduleRepository
        .selectAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    init {
        createRoutineLog()
        updateRoutineLog()
    }

    fun getRoutineDetails(id: Int) = routineRepository.getRoutineDetail(id).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        emptyList()
    )

    private fun updateRoutineLog() {
        routineRepository.selectAll()
            .map { it.filterTodayRoutine() }
            .filter {
                it.isNotEmpty()
            }
            .flatMapLatest {
                updateRoutineLog(it)
            }
            .launchIn(viewModelScope)
    }

    private fun updateRoutineLog(todayRoutine: List<RoutineEntity>) =
        routineLogRepository.getTodayLog()
            .filter { isTodayRoutineLog(it?.date) }
            .onEach { routineLog ->
                if (routineLog != null) {
                    routineLogRepository.update(
                        routineLog.copy(routines = todayRoutine.associateBy { it.id })
                    )
                }
            }

    private fun createRoutineLog() {
        routineRepository.selectAll()
            .map { it.filterTodayRoutine() }
            .filter {
                it.isNotEmpty()
            }
            .flatMapLatest {
                checkRoutineLog(it)
            }
            .launchIn(viewModelScope)
    }

    private fun checkRoutineLog(todayRoutine: List<RoutineEntity>) =
        routineLogRepository.getTodayLog()
            .filter {
                it == null || !isTodayRoutineLog(it.date)
            }
            .onEach {
                createRoutineLog(routineLogRepository, todayRoutine)
            }
}
