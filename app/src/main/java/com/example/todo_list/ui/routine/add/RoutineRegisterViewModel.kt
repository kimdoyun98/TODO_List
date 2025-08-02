package com.example.todo_list.ui.routine.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.alarm.Alarm
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.room.RoutineEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class RoutineRegisterViewModel @Inject constructor(
    private val repository: RoutineRepository,
    private val alarm: Alarm
) : ViewModel() {
    private val checkedDayList = MutableList(7) { false }
    private val time = Array(2) { -1 }

    private val _checkedDayText = MutableStateFlow<String>("")
    val checkedDayText: StateFlow<String> = _checkedDayText

    fun setTime(hourOfDay: Int, minute: Int) {
        time[0] = hourOfDay
        time[1] = minute
    }

    fun checkDay(index: Int, isChecked: Boolean) {
        checkedDayList[index] = isChecked
    }

    fun changeCheckedDayText(daily: String, days: Array<String>) {
        viewModelScope.launch {
            if (checkedDayList.count { it } == 7) _checkedDayText.emit(daily)
            else {
                _checkedDayText.emit(
                    checkedDayList
                        .mapIndexed { index, checked -> if (checked) days[index] else null }
                        .filterNotNull()
                        .joinToString(" ")
                )
            }
        }
    }

    fun insert(title: String) {
        if (time.contains(-1)) {
            val cal = Calendar.getInstance()
            time[0] = cal.get(Calendar.HOUR_OF_DAY)
            time[1] = cal.get(Calendar.MINUTE)
        }
        val time2 = time.map { "%02d".format(it) }

        viewModelScope.launch {
            val id = repository.insert(
                RoutineEntity(
                    title = title,
                    day = checkedDayList,
                    time = "${time2[0]}:${time2[1]}"
                )
            )

            setAlarm(title, time2)
        }
    }

    private fun setAlarm(title: String, time: List<String>) {
        viewModelScope.launch {
            val id = repository.getId(title)
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

            try {
                alarm.setAlarm(
                    hour = time[0].toInt(),
                    minute = time[1].toInt(),
                    alarm_code = id,
                    content = title,
                    todayAlarm = checkedDayList[today - 1]
                )
            } catch (e: Throwable) {
                Log.e("RoutineRegisterActivity", e.message.toString())
            }
        }
    }
}
