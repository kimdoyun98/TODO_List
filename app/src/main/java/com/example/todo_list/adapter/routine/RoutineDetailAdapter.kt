package com.example.todo_list.adapter.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.databinding.RoutineDetailItemBinding

class RoutineDetailAdapter(

) :
    ListAdapter<RoutineDetailEntity, RoutineDetailAdapter.RoutineDetailAdapterViewHolder>(
        ItemDiffCallback(
            onItemsTheSame = { oldItem, newItem -> oldItem.number == newItem.number },
            onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    inner class RoutineDetailAdapterViewHolder(
        private val binding: RoutineDetailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(routineDetail: RoutineDetailEntity) {

        }
    }

    override fun onBindViewHolder(holder: RoutineDetailAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoutineDetailAdapterViewHolder {
        return RoutineDetailAdapterViewHolder(
            RoutineDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
