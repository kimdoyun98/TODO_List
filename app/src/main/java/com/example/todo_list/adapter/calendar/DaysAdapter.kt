package com.example.todo_list.adapter.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import com.example.todo_list.databinding.CalendarDayItemBinding
import com.example.todo_list.ui.calendar.view.Calendar.OnDayClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DaysAdapter(
    private val context: Context,
    private val onClick: OnDayClickListener,
    private val year: Int,
    private val month: Int,
    private val dayList: MutableList<MonthAdapter.Day?>,
    private val calendarLine: CalendarLine
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {
    private val _clickDay = MutableStateFlow<Int>(-1)
    private val clickDay = _clickDay.asStateFlow()

    inner class DaysViewHolder(
        private val binding: CalendarDayItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (clickDay.value != adapterPosition) {
                    _clickDay.value = adapterPosition
                    onClick.onClick(year, month+1, dayList[adapterPosition]!!.date)
                } else {
                    _clickDay.value = -1
                    onClick.onClick(-1, -1, -1)
                }
            }
        }

        fun bind(position: Int) {
            CoroutineScope(Dispatchers.Main.immediate).launch {
                clickDay.collect {
                    if (it == position) {
                        binding.root.background = context.getDrawable(R.drawable.calendar_click)
                    } else {
                        binding.root.background = null
                    }
                }
            }

            //날짜 표시
            binding.day.text = dayList[position]?.date.toString()
            if (month != dayList[position]?.month) {
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
