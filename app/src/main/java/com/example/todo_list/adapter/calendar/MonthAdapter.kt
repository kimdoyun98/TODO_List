package com.example.todo_list.adapter.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.CalendarDaysItemBinding
import com.example.todo_list.ui.calendar.view.Calendar.OnDayClickListener
import java.util.Calendar
import java.util.Date

class MonthAdapter(
    private val context: Context,
    private val onClick: OnDayClickListener
) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
    private var schedule = emptyList<ScheduleEntity>()

    inner class MonthViewHolder(
        private val binding: CalendarDaysItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val calendar = Calendar.getInstance()

        fun bind() {
            //달 구하기
            calendar.time = Date() //현재 날짜 초기화
            calendar.set(Calendar.DAY_OF_MONTH, 1) //스크롤시 현재 월의 1일로 이동
            calendar.add(Calendar.MONTH, adapterPosition - 15) //스크롤시 포지션 만큼 달이동

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val (dayList, calendarLine) = setDayList(month)

            val adapter = DaysAdapter(context, onClick, year, month, dayList, calendarLine)
            binding.daysRecycler.adapter = adapter

            adapter.addSchedule(filterToMonth(year, month + 1))
        }

        private fun setDayList(month: Int): Pair<MutableList<Day?>, CalendarLine> {
            //6주 7일로 날짜를 표시
            val dayList: MutableList<Day?> = MutableList(6 * 7) { null }
            var state = false
            var index = 0

            for (i in 0..5) {
                for (k in 0..6) {
                    //요일 표시
                    calendar.add(
                        Calendar.DAY_OF_MONTH,
                        (1 - calendar.get(Calendar.DAY_OF_WEEK)) + k
                    )
                    val day = Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH))
                    dayList[i * 7 + k] = day

                    if (day.month == month) state = true

                    if (state && day.month != month) {
                        index = i * 7 + k
                        state = false
                    }
                }
                //주 표시
                calendar.add(Calendar.WEEK_OF_MONTH, 1)
            }

            val calendarLine = if (index > 35) CalendarLine.SIX else CalendarLine.FIVE

            return dayList to calendarLine
        }

        private fun filterToMonth(year: Int, month: Int): List<ScheduleEntity> {
            return schedule.filter {
                val scheduleStartYear = it.start_date?.year
                val scheduleStartMonth = it.start_date?.monthValue
                val scheduleEndYear = it.end_date?.year
                val scheduleEndMonth = it.end_date?.monthValue

                (scheduleStartYear == year && scheduleStartMonth == month)
                        || (scheduleEndYear == year && scheduleEndMonth == month)
            }.sortedBy { it.start_date }
        }
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            CalendarDaysItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 30

    fun addSchedule(scheduleData: List<ScheduleEntity>) {
        schedule = scheduleData
        notifyDataSetChanged()
    }

    data class Day(
        val date: Int,
        val month: Int
    )
}
