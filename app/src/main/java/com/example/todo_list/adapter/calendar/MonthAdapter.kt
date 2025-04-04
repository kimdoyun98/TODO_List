package com.example.todo_list.adapter.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.databinding.CalendarDaysItemBinding
import java.util.Calendar
import java.util.Date

class MonthAdapter : RecyclerView.Adapter<MonthAdapter.DaysViewHolder>() {
    inner class DaysViewHolder(
        private val binding: CalendarDaysItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val calendar = Calendar.getInstance()

        fun bind() {
            //달 구하기
            calendar.time = Date() //현재 날짜 초기화
            calendar.set(Calendar.DAY_OF_MONTH, 1) //스크롤시 현재 월의 1일로 이동
            calendar.add(Calendar.MONTH, adapterPosition - 15) //스크롤시 포지션 만큼 달이동

            val tempMonth = calendar.get(Calendar.MONTH)

            //일 구하기
            //6주 7일로 날짜를 표시
            val dayList: MutableList<Day?> = MutableList(6 * 7) { null }
            var state = false
            var index = 0
            for (i in 0..5) { //주
                for (k in 0..6) { //요일
                    //요일 표시
                    calendar.add(
                        Calendar.DAY_OF_MONTH,
                        (1 - calendar.get(Calendar.DAY_OF_WEEK)) + k
                    )
                    val day = Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH))
                    dayList[i * 7 + k] = day

                    if (day.month == tempMonth) state = true

                    if (state && day.month != tempMonth) {
                        index = i * 7 + k
                        state = false
                    }
                }
                //주 표시
                calendar.add(Calendar.WEEK_OF_MONTH, 1)
            }

            val calendarLine = if (index > 35) CalendarLine.SIX else CalendarLine.FIVE
            val adapter = DaysAdapter(tempMonth, dayList, calendarLine)
            binding.daysRecycler.adapter = adapter
        }
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        return DaysViewHolder(
            CalendarDaysItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 30

    data class Day(
        val date: Int,
        val month: Int
    )
}
