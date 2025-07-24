package com.example.todo_list.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.databinding.RecyclerviewHomeItemBinding

class HomeRoutineAdapter : ListAdapter<RoutineEntity, HomeRoutineAdapter.HomeRoutineViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    private lateinit var onClick: (Int) -> Unit

    inner class HomeRoutineViewHolder(
        private val binding: RecyclerviewHomeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
        }

        fun bind(routineEntity: RoutineEntity) {
            binding.routineEntity = routineEntity
            binding.items = currentList
            binding.index = adapterPosition
        }
    }

    fun setClickEvent(onClick: (Int) -> Unit) {
        this.onClick = onClick
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
