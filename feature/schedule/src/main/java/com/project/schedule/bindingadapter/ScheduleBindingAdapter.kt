package com.project.schedule.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.model.Schedule
import com.project.schedule.ScheduleAdapter

object ScheduleBindingAdapter {
    private val adapter = ScheduleAdapter()

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<Schedule>,
        onClick: (Schedule) -> Any
    ) {
        view.adapter = adapter
        adapter.setDialogEvent { schedule -> onClick(schedule) }
        adapter.submitList(items)
    }
}
