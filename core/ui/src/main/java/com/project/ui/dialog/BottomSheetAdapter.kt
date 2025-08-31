package com.project.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.core.ui.R
import com.project.core.ui.databinding.BottomsheetMenuItemBinding
import com.project.ui.ItemDiffCallback

class BottomSheetAdapter(
    private val onClickUpdate: () -> Unit = {},
    private val onClickDelete: () -> Unit = {},
    private val onClickDone: () -> Unit = {}
) : ListAdapter<MenuList, BottomSheetAdapter.BottomSheetViewHolder>(
    ItemDiffCallback(
        onItemsTheSame = { oldItem, newItem -> oldItem.title == newItem.title },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    inner class BottomSheetViewHolder(
        private val binding: BottomsheetMenuItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val pos: Int = adapterPosition
                when (getItem(pos)!!) {
                    MenuList.UPDATE -> {
                        onClickUpdate()
                    }

                    MenuList.DELETE -> {
                        onClickDelete()
                    }

                    MenuList.DONE -> {
                        onClickDone()
                    }
                }
            }
        }

        fun bind(menu: MenuList) {
            binding.menuTitle.text = menu.title
            when (menu) {
                MenuList.UPDATE -> {
                    binding.menuIcon.setImageResource(R.drawable.update)
                }

                MenuList.DELETE -> {
                    binding.menuIcon.setImageResource(R.drawable.delete)
                }

                MenuList.DONE -> {
                    binding.menuIcon.setImageResource(R.drawable.done)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        return BottomSheetViewHolder(
            BottomsheetMenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
