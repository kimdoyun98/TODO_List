package com.example.todo_list.adapter.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.databinding.RoutineDetailAddItemBinding

class RoutineDetailAddAdapter(
    private val onChangeText: (Int, String) -> Unit,
    private val onClickDelete: (Int) -> Unit
) :
    ListAdapter<RoutineDetailEntity, RoutineDetailAddAdapter.RoutineDetailAddAdapterViewHolder>(
        ItemDiffCallback(
            onItemsTheSame = { oldItem, newItem -> oldItem.number == newItem.number },
            onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    inner class RoutineDetailAddAdapterViewHolder(
        private val binding: RoutineDetailAddItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.routineDetailEdit.addTextChangedListener { title ->
                onChangeText(adapterPosition, "$title")
            }

            binding.routineDetailDeleteBt.setOnClickListener {
                onClickDelete(adapterPosition)
            }
        }

        fun bind() {}
    }

    override fun onBindViewHolder(holder: RoutineDetailAddAdapterViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoutineDetailAddAdapterViewHolder {
        return RoutineDetailAddAdapterViewHolder(
            RoutineDetailAddItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
