package com.example.todo_list.ui.schedule.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.schedule.ScheduleAdapter
import com.example.todo_list.data.room.ScheduleEntity

object ScheduleBindingAdapter {
    private val adapter = ScheduleAdapter()

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<ScheduleEntity>,
        onClick: (ScheduleEntity) -> Any
    ) {
        view.adapter = adapter
        adapter.setDialogEvent { scheduleEntity -> onClick(scheduleEntity) }
        adapter.submitList(items)
    }
}
