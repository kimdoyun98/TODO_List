package com.example.todo_list.adapter.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.databinding.RoutineDetailItemBinding

class RoutineDetailAdapter(
    private val addTitle: (Int, String) -> Unit
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
            binding.routineDetailEdit.addTextChangedListener { title ->
                addTitle(absoluteAdapterPosition, "$title")
            }
        }

        fun bind(routineDetail: RoutineDetailEntity) {
            binding.number = (routineDetail.number + 1).toString()
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
