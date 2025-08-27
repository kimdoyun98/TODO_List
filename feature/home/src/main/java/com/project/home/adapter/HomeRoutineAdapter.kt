package com.project.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.data.local.room.entity.RoutineEntity
import com.project.feature.home.databinding.RecyclerviewHomeItemBinding

class HomeRoutineAdapter : RecyclerView.Adapter<HomeRoutineAdapter.HomeRoutineViewHolder>() {
    private var currentList: List<RoutineEntity> = emptyList()
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

    fun submitList(list: List<RoutineEntity>) {
        this.currentList = list
        notifyDataSetChanged()
    }

    fun setClickEvent(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: HomeRoutineViewHolder, position: Int) {
        holder.bind(currentList[position])
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

    override fun getItemCount(): Int = currentList.size
}
