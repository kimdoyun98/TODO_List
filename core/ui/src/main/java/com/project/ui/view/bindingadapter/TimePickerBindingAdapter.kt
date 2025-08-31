package com.project.ui.view.bindingadapter

import android.widget.TimePicker
import androidx.databinding.BindingAdapter

object TimePickerBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:onTimeChanged")
    fun onTimeChangedTimePicker(view: TimePicker, changed: (Int, Int) -> Unit) {
        view.setOnTimeChangedListener { _, hourOfDay, minute ->
            changed(hourOfDay, minute)
        }
    }
}
