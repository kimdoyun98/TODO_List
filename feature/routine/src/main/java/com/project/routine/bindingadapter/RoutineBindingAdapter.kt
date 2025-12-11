package com.project.routine.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.model.Routine
import com.project.routine.RoutineAdapter

internal object RoutineBindingAdapter {
    private val adapter = RoutineAdapter()

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<Routine>,
        onClick: (Routine) -> Any,
    ) {
        view.adapter = adapter

        adapter.setItemClick { routine -> onClick(routine) }

        adapter.submitList(items)
    }
}
