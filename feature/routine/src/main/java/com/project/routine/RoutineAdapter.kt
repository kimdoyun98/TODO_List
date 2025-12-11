package com.project.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.feature.routine.databinding.RecyclerviewRoutineItemBinding
import com.project.model.Routine
import com.project.ui.ItemDiffCallback

internal class RoutineAdapter : ListAdapter<Routine, RoutineAdapter.RoutineViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    private lateinit var onClick: (Routine) -> Any

    inner class RoutineViewHolder(
        private val binding: RecyclerviewRoutineItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val pos: Int = adapterPosition
                onClick(getItem(pos))
            }
        }

        fun bind(routineEntity: Routine) {
            binding.routineEntity = routineEntity
        }
    }

    fun setItemClick(onClick: (Routine) -> Any) {
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
