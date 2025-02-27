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
    private val onChangeText: (Int, String) -> Unit,
    private val onClickDelete: (Int) -> Unit
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
                onChangeText(absoluteAdapterPosition, "$title")
            }

            binding.routineDetailDeleteBt.setOnClickListener {
                onClickDelete(absoluteAdapterPosition)
            }
        }

        fun bind() {}
    }

    override fun onBindViewHolder(holder: RoutineDetailAdapterViewHolder, position: Int) {
        holder.bind()
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
