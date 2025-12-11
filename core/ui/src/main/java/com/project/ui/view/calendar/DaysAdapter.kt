package com.project.ui.view.calendar

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.core.ui.R
import com.project.core.ui.databinding.CalendarDayItemBinding
import com.project.model.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class DaysAdapter(
    private val context: Context,
    private val onClick: Calendar.OnDayClickListener,
    private val year: Int,
    private val month: Int,
    private val dayList: MutableList<MonthAdapter.Day?>,
    private val calendarLine: CalendarLine
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {
    private val _clickDay = MutableStateFlow<Int>(-1)
    private val clickDay = _clickDay.asStateFlow()
    private var schedule = emptyList<Schedule>()
    private val schedulePosition = HashMap<Int, Int>()

    inner class DaysViewHolder(
        private val binding: CalendarDayItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (clickDay.value != adapterPosition) {
                    _clickDay.value = adapterPosition
                    onClick.onClick(year, month + 1, dayList[adapterPosition]!!.date)
                } else {
                    _clickDay.value = -1
                    onClick.onClick(-1, -1, -1)
                }
            }
        }

        fun bind(position: Int) {
            setDateClickEvent(position)
            setCalendarDate(position)
            setWeekendColor(position)

            val day = dayList[position]?.date!!
            val checked = Array<Boolean>(4) { false }
            val todaySchedule = schedule.filter {
                val startDate = it.start_date?.dayOfMonth!!
                val endDate = it.end_date?.dayOfMonth!!

                day in startDate..endDate
            }

            todaySchedule.filter {
                schedulePosition[it.id] != null
            }.forEach { schedule ->
                setSchedule(schedulePosition[schedule.id]!!, schedule, day)
                checked[schedulePosition[schedule.id]!!] = true
            }

            todaySchedule.filter {
                schedulePosition[it.id] == null
            }.forEach { schedule ->
                for (pos in checked.indices) {
                    if (checked[pos]) continue

                    schedulePosition[schedule.id] = pos
                    setSchedule(pos, schedule, day)
                    checked[pos] = true
                    break
                }
            }
        }

        private fun setSchedule(position: Int, schedule: Schedule, day: Int) {
            val view = when (position) {
                0 -> binding.schedule1
                1 -> binding.schedule2
                2 -> binding.schedule3
                else -> binding.schedule4
            }

            if (schedule.start_date == schedule.end_date) {
                view.setBackgroundResource(R.drawable.calendar_schedule_single)
                val drawable: GradientDrawable = view.background as GradientDrawable
                drawable.setColor(schedule.color!!)
                return
            }

            val startDate = schedule.start_date?.dayOfMonth!!
            val endDate = schedule.end_date?.dayOfMonth!!
            if (startDate == day) {
                view.setBackgroundResource(R.drawable.calendar_schedule_multi_start)
                val drawable: GradientDrawable = view.background as GradientDrawable
                drawable.setColor(schedule.color!!)
            } else if (endDate == day) {
                view.setBackgroundResource(R.drawable.calendar_schedule_multi_end)
                val drawable: GradientDrawable = view.background as GradientDrawable
                drawable.setColor(schedule.color!!)
            } else {
                view.setBackgroundColor(schedule.color!!)
            }
        }

        private fun setDateClickEvent(position: Int) {
            CoroutineScope(Dispatchers.Main.immediate).launch {
                clickDay.collect {
                    if (it == position) {
                        binding.root.background = context.getDrawable(R.drawable.calendar_click)
                    } else {
                        binding.root.background = null
                    }
                }
            }
        }

        private fun setCalendarDate(position: Int) {
            binding.day.text = dayList[position]?.date.toString()
            if (month != dayList[position]?.month) {
                binding.day.alpha = 0.4f
            }
        }

        private fun setWeekendColor(position: Int) {
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

    fun addSchedule(scheduleData: List<Schedule>) {
        schedule = scheduleData
        notifyDataSetChanged()
    }
}

enum class CalendarLine {
    FIVE, SIX
}
