package com.example.todo_list.ui.view

import androidx.databinding.BindingAdapter
import com.example.todo_list.data.room.RoutineEntity

object SideLineBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:items", "app:item_index", requireAll = true)
    fun sideLineBinding(view: SideLine, items: List<RoutineEntity>, index: Int) {
        setPosition(view, items, index)
    }

    @JvmStatic
    @BindingAdapter("app:items", "app:item_index", requireAll = true)
    fun progressSideLineState(view: ProgressSideLine, items: List<RoutineEntity>, index: Int) {
        setPosition(view, items, index)
        view.setProgressState(
            when (items[index].success) {
                true -> ProgressSideLine.ProgressState.Success
                false -> ProgressSideLine.ProgressState.Fail
                null -> {
                    if (index == 0 || items[index - 1].success != null) ProgressSideLine.ProgressState.Do
                    else ProgressSideLine.ProgressState.Wait
                }
            }
        )
    }

    private fun setPosition(view: SideLine, items: List<RoutineEntity>, index: Int) {
        view.setPosition(
            when (index) {
                0 -> {
                    if (items.size == 1) SideLine.Position.ONE
                    else SideLine.Position.START
                }

                items.lastIndex -> {
                    SideLine.Position.END
                }

                else -> {
                    SideLine.Position.MID
                }
            }
        )
    }
}
