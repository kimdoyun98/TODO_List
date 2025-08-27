package com.project.routine.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.data.local.room.entity.RoutineEntity
import com.project.routine.RoutineAdapter

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
