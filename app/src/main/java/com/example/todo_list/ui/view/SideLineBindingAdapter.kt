package com.example.todo_list.ui.view

import androidx.databinding.BindingAdapter

object SideLineBindingAdapter {
    @JvmStatic
    @BindingAdapter("itemPosition")
    fun sideLineBinding(view: SideLine, position: Position) {
        view.setPosition(position)
    }
}
