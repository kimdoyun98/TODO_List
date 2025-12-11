package com.project.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.feature.schedule.databinding.RecyclerviewTodoItemBinding
import com.project.model.Schedule
import com.project.ui.ItemDiffCallback

internal class ScheduleAdapter : ListAdapter<Schedule, ScheduleAdapter.ScheduleViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    private lateinit var showDialog: (Schedule) -> Unit

    inner class ScheduleViewHolder(
        private val binding: RecyclerviewTodoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val pos: Int = adapterPosition
                showDialog(getItem(pos))
            }
        }

        fun bind(toDoEntity: Schedule) {
            binding.todoEntity = toDoEntity
        }
    }

    fun setDialogEvent(showDialog: (Schedule) -> Unit) {
        this.showDialog = showDialog
    }

    // 여기서 set 설정
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            RecyclerviewTodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
