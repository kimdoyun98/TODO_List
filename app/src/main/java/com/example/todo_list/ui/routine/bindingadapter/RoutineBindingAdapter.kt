package com.example.todo_list.ui.routine.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.routine.RoutineAdapter
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.ui.routine.RoutineViewModel

object RoutineBindingAdapter {
    private val adapter = RoutineAdapter()

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick", "app:viewModel")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<RoutineEntity>,
        onClick: (RoutineEntity) -> Any,
        viewModel: RoutineViewModel
    ) {
        view.adapter = adapter

        adapter.setItemClick { routineEntity -> onClick(routineEntity) }

        adapter.getRoutineDetails { id: Int -> viewModel.getRoutineDetails(id) }

        adapter.setScope(viewModel.viewModelScope)

        adapter.submitList(items)
    }
}
