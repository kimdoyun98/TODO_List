package com.example.todo_list.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.log.StatisticsLogRepository
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.PeriodRoutineLog
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.data.room.RoutineLog
import com.example.todo_list.ui.home.utils.PeriodStatistics
import com.example.todo_list.ui.home.utils.StatisticsTab
import com.example.todo_list.ui.home.utils.createLogStatisticsLog
import com.example.todo_list.ui.home.utils.filterTodayRoutine
import com.example.todo_list.ui.home.utils.isTodayRoutineLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val routineRepository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
    private val statisticsLogRepository: StatisticsLogRepository,
) : ViewModel() {

    private val _todayRoutineLog = MutableStateFlow<RoutineLog?>(null)
    private val todayRoutineLog = _todayRoutineLog.asStateFlow()

    val todayRoutine = routineLogRepository.getTodayLog()
        .onEach { _todayRoutineLog.value = it }
        .map { it?.routines?.values?.toList() ?: emptyList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    val recentSchedule = scheduleRepository
        .getRecentSchedule()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            null
        )

    private val _selectedTab: MutableStateFlow<StatisticsTab> = MutableStateFlow(StatisticsTab.WEEK)
    private val selectedTab = _selectedTab.asStateFlow()

    val changedTab = { tab: StatisticsTab ->
        _selectedTab.value = tab
    }

    private val _periodLog = MutableStateFlow<List<PeriodRoutineLog>>(emptyList())
    val periodLog = _periodLog.asStateFlow()

    private val _periodStatistics = MutableStateFlow<PeriodStatistics>(PeriodStatistics())
    val periodStatistics = _periodStatistics.asStateFlow()

    init {
        createRoutineLog()
        updateRoutineLog()

        selectedTab
            .onEach {
                getPeriodStatisticsLog(it)
            }
            .launchIn(viewModelScope)

        periodLog
            .onEach {
                updatePeriodStatistics(it)
            }
            .launchIn(viewModelScope)
    }

    fun updateToRoutine(position: Int, success: Boolean) {
        viewModelScope.launch {
            val list = todayRoutine.value.toMutableList()
            val changeRoutine = todayRoutine.value[position].copy(success = success)
            list[position] = changeRoutine

            todayRoutineLog.value?.copy(routines = list.associateBy { it.id })?.let {
                routineLogRepository.update(it)
            }
        }
    }

    private fun updatePeriodStatistics(periodList: List<PeriodRoutineLog>) {
        val total = periodList.sumOf { it.total }
        val success = periodList.sumOf { it.success }
        val fail = total - success

        _periodStatistics.value =
            PeriodStatistics(total = total, success = success, fail = fail)
    }

    private suspend fun getPeriodStatisticsLog(tab: StatisticsTab) {
        val now = LocalDate.now()
        val end = now.minusDays(1)
        val start = when (tab) {
            StatisticsTab.WEEK -> end.minusDays(7)
            StatisticsTab.MONTH -> end.minusMonths(1)
            StatisticsTab.HALF_OF_YEAR -> end.minusMonths(6)
            StatisticsTab.YEAR -> end.minusYears(1)
        }

        _periodLog.value = statisticsLogRepository.getPeriodLog(start, end)
    }

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
                it?.let { createLogStatisticsLog(statisticsLogRepository, it) }
                com.example.todo_list.ui.home.utils.createRoutineLog(
                    routineLogRepository,
                    todayRoutine
                )
            }
}
