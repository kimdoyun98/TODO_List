package com.example.todo_list.ui.schedule

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.ScheduleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    var sortFilter: MutableLiveData<Int> = MutableLiveData<Int>(1)

    // Data Binding 을 위한 Boolean 변수들 (TextStyle, TextColor)
    var isSortedByLatest: ObservableField<Boolean> = ObservableField<Boolean>(true)
    var isSortedByDeadline: ObservableField<Boolean> = ObservableField<Boolean>(false)
    var isSortedByRating: ObservableField<Boolean> = ObservableField<Boolean>(false)

    val getAll: LiveData<List<ScheduleEntity>> = repository.selectAll().asLiveData()

    fun insert(scheduleEntity: ScheduleEntity) =
        viewModelScope.launch { repository.insert(scheduleEntity) }

    fun delete(id: Int) = viewModelScope.launch { repository.delete(id) }

    fun update(scheduleEntity: ScheduleEntity) =
        viewModelScope.launch { repository.update(scheduleEntity) }

    fun success(id: Int) = viewModelScope.launch { repository.success(id) }


    fun onClickSetFilterLATEST() {
        sortFilter.value = LATEST

        isSortedByLatest.set(true)
        isSortedByDeadline.set(false)
        isSortedByRating.set(false)
    }

    fun onClickSetFilterDEADLINE() {
        sortFilter.value = DEADLINE

        isSortedByLatest.set(false)
        isSortedByDeadline.set(true)
        isSortedByRating.set(false)
    }

    fun onClickSetFilterRATING() {
        sortFilter.value = RATING

        isSortedByLatest.set(false)
        isSortedByDeadline.set(false)
        isSortedByRating.set(true)
    }

    companion object {
        const val LATEST = 1
        const val DEADLINE = 2
        const val RATING = 3
    }

}
