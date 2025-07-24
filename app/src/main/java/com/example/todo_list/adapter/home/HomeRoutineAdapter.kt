package com.example.todo_list.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.databinding.RecyclerviewHomeItemBinding
import com.example.todo_list.ui.view.SideLine
import kotlinx.coroutines.flow.StateFlow

class HomeRoutineAdapter(
    private val getRoutineDetails: (Int) -> StateFlow<List<RoutineDetailEntity>>,
    private val scope: LifecycleCoroutineScope
) : ListAdapter<RoutineEntity, HomeRoutineAdapter.HomeRoutineViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    inner class HomeRoutineViewHolder(
        private val binding: RecyclerviewHomeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(routineEntity: RoutineEntity) {
            binding.routineEntity = routineEntity
            binding.position = when (adapterPosition) {
                0 -> {
                    if (currentList.size == 1) SideLine.Position.ONE
                    else SideLine.Position.START
                }

                currentList.lastIndex -> {
                    SideLine.Position.END
                }

                else -> {
                    SideLine.Position.MID
                }
            }

            binding.items = currentList
            binding.index = adapterPosition
        }
    }

    override fun onBindViewHolder(holder: HomeRoutineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRoutineViewHolder {
        return HomeRoutineViewHolder(
            RecyclerviewHomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
