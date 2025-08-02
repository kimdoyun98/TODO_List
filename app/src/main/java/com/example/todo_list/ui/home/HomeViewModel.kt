package com.example.todo_list.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.log.StatisticsLogRepository
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.PeriodRoutineLog
import com.example.todo_list.data.room.RoutineLog
import com.example.todo_list.ui.home.utils.PeriodStatistics
import com.example.todo_list.ui.home.utils.StatisticsTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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
    private val routineLogRepository: RoutineLogRepository,
    private val statisticsLogRepository: StatisticsLogRepository,
) : ViewModel() {

    private val _selectedTab: MutableStateFlow<StatisticsTab> = MutableStateFlow(StatisticsTab.WEEK)
    private val selectedTab = _selectedTab.asStateFlow()

    private val _todayRoutineLog = MutableStateFlow<RoutineLog?>(null)
    private val todayRoutineLog = _todayRoutineLog.asStateFlow()

    private val _periodLog = MutableStateFlow<List<PeriodRoutineLog>>(emptyList())
    private val periodLog = _periodLog.asStateFlow()

    private val _periodStatistics = MutableStateFlow<PeriodStatistics>(PeriodStatistics())
    val periodStatistics = _periodStatistics.asStateFlow()

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

    val changedTab = { tab: StatisticsTab ->
        _selectedTab.value = tab
    }

    init {
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
            val newRoutinesMap = todayRoutineLog.value!!.routines!!.toMutableMap()
            val updateRoutine = todayRoutine.value[position].copy(success = success)
            newRoutinesMap[updateRoutine.id] = updateRoutine

            todayRoutineLog.value?.copy(routines = newRoutinesMap)?.let {
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
}
