package com.example.todo_list.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todo_list.adapter.calendar.CalendarScheduleAdapter
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.FragmentCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var adapter: CalendarScheduleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CalendarScheduleAdapter()
        binding.monthScheduleRv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
    }

    private suspend fun setCalendarScheduleData(scheduleList: List<ScheduleEntity>){
        binding.calendar.currentDate.collect { currentDate ->
            val currentSchedule = scheduleList.filter {
                val scheduleStartYear = it.start_date?.substring(0, 4)?.toInt()
                val scheduleStartMonth = it.start_date?.substring(4, 6)?.toInt()
                val scheduleEndYear = it.deadline_date?.substring(0, 4)?.toInt()
                val scheduleEndMonth = it.deadline_date?.substring(4, 6)?.toInt()

                (scheduleStartYear == currentDate?.year && scheduleStartMonth == currentDate?.month)
                        || (scheduleEndYear == currentDate?.year && scheduleEndMonth == currentDate?.month)
            }

            adapter.submitList(currentSchedule)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
