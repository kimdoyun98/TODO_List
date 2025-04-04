package com.example.todo_list.ui.calendar.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import com.example.todo_list.databinding.CalendarDayItemBinding
import java.util.Calendar

class DaysAdapter(
    private val tempMonth: Int,
    private val dayList: MutableList<MonthAdapter.Day?>,
    private val calendarLine: CalendarLine
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    inner class DaysViewHolder(
        private val binding: CalendarDayItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val calendar = Calendar.getInstance()

        init {
            //TODO 날짜 클릭
        }

        fun bind(position: Int) {
            //날짜 표시
            binding.day.text = dayList[position]?.date.toString()
            if (tempMonth != dayList[position]?.month) {
                binding.day.alpha = 0.4f
            }

            //토요일이면 파란색 || 일요일이면 빨간색으로 색상표시
            if ((position + 1) % 7 == 0) {
                binding.day.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
            } else if (position == 0 || position % 7 == 0) {
                binding.day.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            }
        }
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        return DaysViewHolder(
            CalendarDayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = when (calendarLine) {
        CalendarLine.FIVE -> 35
        CalendarLine.SIX -> 42
    }
}

enum class CalendarLine {
    FIVE, SIX
}
