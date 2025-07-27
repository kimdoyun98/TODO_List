package com.example.todo_list.ui.calendar

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.todo_list.adapter.calendar.CalendarScheduleAdapter
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.FragmentCalendarBinding
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarFragment :
    ViewBindingFragment<FragmentCalendarBinding>(FragmentCalendarBinding::inflate) {
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var adapter: CalendarScheduleAdapter

    override fun initView() {
        adapter = CalendarScheduleAdapter()
        binding.monthScheduleRv.adapter = adapter

        fragmentScope {
            launch {
                binding.calendar.selectDay.collect {
                    Log.e("Click Calender", it.toString())
                }
            }

            launch {
                viewModel.allSchedule.collect { scheduleList ->
                    binding.calendar.addSchedule(scheduleList)

                    launch {
                        setCalendarScheduleData(scheduleList)
                    }
                }
            }
        }
    }

    private suspend fun setCalendarScheduleData(scheduleList: List<ScheduleEntity>) {
        binding.calendar.currentDate.collect { currentDate ->
            val currentSchedule = scheduleList.filter {
                val scheduleStartYear = it.start_date?.year
                val scheduleStartMonth = it.start_date?.monthValue
                val scheduleEndYear = it.end_date?.year
                val scheduleEndMonth = it.end_date?.monthValue

                (scheduleStartYear == currentDate?.year && scheduleStartMonth == currentDate?.month)
                        || (scheduleEndYear == currentDate?.year && scheduleEndMonth == currentDate?.month)
            }

            adapter.submitList(currentSchedule)
        }
    }
}
