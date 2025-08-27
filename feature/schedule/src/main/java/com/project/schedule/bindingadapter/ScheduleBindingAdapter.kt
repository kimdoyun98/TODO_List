package com.project.schedule.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.data.local.room.entity.ScheduleEntity
import com.project.schedule.ScheduleAdapter

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
