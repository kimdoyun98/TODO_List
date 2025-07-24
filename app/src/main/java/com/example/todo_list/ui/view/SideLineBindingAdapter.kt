package com.example.todo_list.ui.view

import androidx.databinding.BindingAdapter
import com.example.todo_list.data.room.RoutineEntity

object SideLineBindingAdapter {
    @JvmStatic
    @BindingAdapter("itemPosition")
    fun sideLineBinding(view: SideLine, position: SideLine.Position) {
        view.setPosition(position)
    }

    @JvmStatic
    @BindingAdapter("app:items", "app:item_index", requireAll = true)
    fun progressSideLineState(view: ProgressSideLine, items: List<RoutineEntity>, index: Int){
        view.setProgressState(
            when(items[index].success){
                true -> ProgressSideLine.ProgressState.Success
                false -> ProgressSideLine.ProgressState.Fail
                null -> {
                    if(index == 0 || items[index-1].success != null) ProgressSideLine.ProgressState.Do
                    else ProgressSideLine.ProgressState.Wait
                }
            }
        )
    }
}
