package com.project.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.repository.schedule.ScheduleRepository
import com.project.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    private val _selectedSort = MutableStateFlow<SortItem>(SortItem.LATEST)
    val selectedSort = _selectedSort.asStateFlow()

    private val _sortedScheduleItems = MutableStateFlow<List<Schedule>>(emptyList())
    val sortedScheduleItems = _sortedScheduleItems.asStateFlow()

    init {
        repository.selectAll()
            .flatMapLatest { sorted(it) }
            .launchIn(viewModelScope)
    }

    fun delete(id: Int) = viewModelScope.launch { repository.delete(id) }

    fun update(scheduleEntity: Schedule) =
        viewModelScope.launch { repository.update(scheduleEntity) }

    fun success(id: Int) = viewModelScope.launch { repository.success(id) }


    fun onClickSetFilterLATEST() {
        _selectedSort.value = SortItem.LATEST
    }

    fun onClickSetFilterDEADLINE() {
        _selectedSort.value = SortItem.END_DATE
    }

    private fun sorted(items: List<Schedule>) = selectedSort
        .onEach {
            val sortedItems =
                when (it) {
                    SortItem.LATEST -> {
                        items
                            .sortedByDescending { item -> item.start_date }
                            .reversed()
                    }

                    SortItem.END_DATE -> {
                        items
                            .sortedByDescending { item -> item.end_date }
                            .reversed()
                    }
                }

            _sortedScheduleItems.value = sortedItems
        }

    enum class SortItem {
        LATEST, END_DATE
    }
}
