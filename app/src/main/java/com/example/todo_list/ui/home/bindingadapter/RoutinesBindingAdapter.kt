package com.example.todo_list.ui.home.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.home.HomeRoutineAdapter
import com.example.todo_list.data.room.RoutineEntity

object RoutinesBindingAdapter {
    private val adapter = HomeRoutineAdapter()

    @JvmStatic
    @BindingAdapter("app:adapter")
    fun setAdapterRecyclerView(view: RecyclerView, items: List<RoutineEntity>) {
        view.adapter = adapter
        adapter.submitList(items.sortedBy { it.time })
    }
}
