package com.example.todo_list.ui.schedule.add

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.ScheduleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleAddViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _endDateState = MutableStateFlow(false)
    val endDateState = _endDateState.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private val _endDate = MutableStateFlow("")
    val endDate = _endDate.asStateFlow()

    private val _registerButtonEnabled = MutableStateFlow(false)
    val registerButtonEnabled = _registerButtonEnabled.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _finish = MutableStateFlow<Boolean>(false)
    val finish = _finish.asStateFlow()

    val titleChanged = { title: String -> _title.value = title }
    val dateChanged = { date: String -> _date.value = date }
    val endDateChanged = { date: String -> _endDate.value = date }

    init {
        date
            .onEach { _registerButtonEnabled.update { false } }
            .map { it.length == 10 }
            .flatMapLatest { dateState ->
                endDateState
                    .flatMapLatest { state ->
                        endDate
                            .onEach { _registerButtonEnabled.update { false } }
                            .map { !state || it.length == 10 }
                    }
                    .filter { endDateState ->
                        dateState && endDateState
                    }
            }
            .onEach {
                _registerButtonEnabled.update { true }
            }
            .launchIn(viewModelScope)
    }

    fun endDateStateChanged() {
        _endDateState.value = true
    }

    fun endDateClosed() {
        _endDateState.value = false
        _endDate.value = ""
    }

    fun insert() {
        viewModelScope.launch {
            runCatching {
                ScheduleEntity(
                    title = title.value,
                    start_date = LocalDate.parse(date.value),
                    end_date = if (endDateState.value) LocalDate.parse(endDate.value)
                    else LocalDate.parse(date.value),
                    color = getRandomColor()
                )
            }.onSuccess {
                repository.insert(it)
                _finish.update { true }
            }.onFailure {
                _toastMessage.emit("날짜 범위가 올바르지 않습니다.")
            }
        }
    }

    private fun getRandomColor(): Int {
        val range = (0..255)
        return Color.argb(255, range.random(), range.random(), range.random())
    }
}
