package com.project.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.data.local.room.entity.RoutineEntity
import com.project.feature.routine.databinding.RecyclerviewRoutineItemBinding
import com.project.ui.ItemDiffCallback

class RoutineAdapter : ListAdapter<RoutineEntity, RoutineAdapter.RoutineViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    private lateinit var onClick: (RoutineEntity) -> Any

    inner class RoutineViewHolder(
        private val binding: RecyclerviewRoutineItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val pos: Int = adapterPosition
                onClick(getItem(pos))
            }
        }

        fun bind(routineEntity: RoutineEntity) {
            binding.routineEntity = routineEntity
        }
    }

    fun setItemClick(onClick: (RoutineEntity) -> Any) {
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        return RoutineViewHolder(
            RecyclerviewRoutineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
