package com.example.todo_list.ui.routine.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.routine.RoutineAdapter
import com.example.todo_list.data.room.RoutineEntity

object RoutineBindingAdapter {
    private val adapter = RoutineAdapter()

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<RoutineEntity>,
        onClick: (RoutineEntity) -> Any,
    ) {
        view.adapter = adapter

        adapter.setItemClick { routineEntity -> onClick(routineEntity) }

        adapter.submitList(items)
    }
}
