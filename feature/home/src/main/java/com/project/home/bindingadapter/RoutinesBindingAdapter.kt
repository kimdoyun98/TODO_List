package com.project.home.bindingadapter

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.home.adapter.HomeRoutineAdapter
import com.project.model.Routine

internal object RoutinesBindingAdapter {
    private val adapter = HomeRoutineAdapter()
    private val EMPTY_ROUTINE = listOf(Routine(title = "일정이 없습니다.", day = emptyList(), time = ""))

    @JvmStatic
    @BindingAdapter("app:items", "app:onClick")
    fun setAdapterRecyclerView(
        view: RecyclerView,
        items: List<Routine>,
        onClick: (Int, String?, Context) -> Any
    ) {
        view.adapter = adapter
        adapter.setClickEvent { position ->
            onClick(position, items[position].title, view.context)
        }
        adapter.submitList(if (items.isEmpty()) EMPTY_ROUTINE else items.sortedBy { it.time })
    }
}
