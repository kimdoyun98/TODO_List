package com.example.todo_list.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.data.room.RoutineLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val repository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
) : ViewModel() {
    val getAll: StateFlow<List<RoutineEntity>> = repository.selectAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        emptyList()
    )

    private val _todayRoutineLog = MutableStateFlow<RoutineLog?>(null)
    private val todayRoutineLog = _todayRoutineLog.asStateFlow()

    init {
        routineLogRepository.getTodayLog()
            .onEach { _todayRoutineLog.value = it }
            .launchIn(viewModelScope)
    }

    fun update() = viewModelScope.launch { repository.update() }

    fun todaySuccess(id: Int) = viewModelScope.launch {
        val routines = todayRoutineLog.value?.routines?.toMutableMap() ?: return@launch
        routines[id]?.success = true

        routineLogRepository.update(
            todayRoutineLog.value!!.copy(routines = routines)
        )
    }

    fun delete(id: Int) = viewModelScope.launch { repository.delete(id) }
}
