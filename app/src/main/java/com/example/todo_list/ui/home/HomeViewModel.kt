package com.example.todo_list.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val routineRepository: RoutineRepository
) : ViewModel() {
    private val calendar = Calendar.getInstance()
    private val day = calendar.get(Calendar.DAY_OF_WEEK)

    val getRoutineAll = routineRepository.selectAll()
        .map {
            it.filter { data -> data.day?.get(day - 1) ?: false }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    fun getRoutineDetails(id: Int) = routineRepository.getRoutineDetail(id).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        emptyList()
    )

    val getScheduleAll = scheduleRepository
        .selectAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
}
