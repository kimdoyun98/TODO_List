package com.project.calendar

import android.util.Log
import androidx.fragment.app.viewModels
import com.project.data.local.room.entity.ScheduleEntity
import com.project.feature.calendar.databinding.FragmentCalendarBinding
import com.project.ui.base.ViewBindingFragment
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
