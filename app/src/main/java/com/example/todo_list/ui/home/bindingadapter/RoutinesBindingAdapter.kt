package com.example.todo_list.ui.home.bindingadapter

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.adapter.home.HomeRoutineAdapter
import com.example.todo_list.data.room.RoutineEntity

object RoutinesBindingAdapter {
    private val adapter = HomeRoutineAdapter()
    private val EMPTY_ROUTINE = listOf(RoutineEntity(title = "일정이 없습니다.", day = null, time = ""))

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<RoutineEntity>,
        onClick: (Int, String?, Context) -> Any
    ) {
        view.adapter = adapter
        adapter.setClickEvent { position ->
            onClick(position, items[position].title, view.context)
        }
        adapter.submitList(if (items.isEmpty()) EMPTY_ROUTINE else items.sortedBy { it.time })
    }
}
