package com.example.todo_list.adapter.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.CalendarScheduleItemBinding

class CalendarScheduleAdapter :
    ListAdapter<ScheduleEntity, CalendarScheduleAdapter.CalendarScheduleViewHolder>(
        ItemDiffCallback(
            onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    inner class CalendarScheduleViewHolder(
        val binding: CalendarScheduleItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: ScheduleEntity) {
            binding.color.setBackgroundColor(schedule.color!!)
            binding.scheduleTitleTv.text = schedule.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarScheduleAdapter.CalendarScheduleViewHolder {
        return CalendarScheduleViewHolder(
            CalendarScheduleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
