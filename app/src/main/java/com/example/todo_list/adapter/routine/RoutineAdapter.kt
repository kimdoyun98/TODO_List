package com.example.todo_list.adapter.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.ItemDiffCallback
import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.databinding.RecyclerviewCycleItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoutineAdapter : ListAdapter<RoutineEntity, RoutineAdapter.RoutineViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    private lateinit var onClick: (RoutineEntity) -> Any
    private lateinit var getRoutineDetails: (Int) -> StateFlow<List<RoutineDetailEntity>>
    private lateinit var scope: CoroutineScope

    inner class RoutineViewHolder(
        private val binding: RecyclerviewCycleItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val pos: Int = adapterPosition
                onClick(getItem(pos))
            }
        }

        fun bind(routineEntity: RoutineEntity) {
            binding.routineEntity = routineEntity

            val adapter = RoutineDetailAdapter()
            binding.routineDetailRv.adapter = adapter
            scope.launch {
                getRoutineDetails(routineEntity.id).collect { routineDetailList ->
                    adapter.submitList(routineDetailList.sortedBy { it.number })
                }
            }
        }
    }

    fun setItemClick(onClick: (RoutineEntity) -> Any) {
        this.onClick = onClick
    }

    fun getRoutineDetails(details: (Int) -> StateFlow<List<RoutineDetailEntity>>) {
        this.getRoutineDetails = details
    }

    fun setScope(scope: CoroutineScope) {
        this.scope = scope
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        return RoutineViewHolder(
            RecyclerviewCycleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
