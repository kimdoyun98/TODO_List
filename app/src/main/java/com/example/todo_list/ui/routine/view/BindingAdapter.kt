package com.example.todo_list.ui.routine.view

import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("itemPosition")
    fun sideLineBinding(view: SideLine, position: Position) {
        view.setPosition(position)
    }
}
