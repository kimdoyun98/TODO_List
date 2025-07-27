package com.example.todo_list.ui.schedule.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.ScheduleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleAddViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    val titleChanged = { title: String -> _title.value = title }

    fun insert(scheduleEntity: ScheduleEntity) =
        viewModelScope.launch { repository.insert(scheduleEntity) }

}
