package com.project.routine.bindingadapter

import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.project.core.ui.R

object WeekToggleBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:onChecked")
    fun setOnCheckedChangeListener(
        view: ToggleButton,
        onChecked: (Int, Boolean) -> Unit,
    ) {
        view.setOnCheckedChangeListener { buttonView, isChecked ->
            with(view.context) {
                when (buttonView?.text) {
                    getString(R.string.sunday) -> onChecked(0, isChecked)
                    getString(R.string.monday) -> onChecked(1, isChecked)
                    getString(R.string.tuesday) -> onChecked(2, isChecked)
                    getString(R.string.wednesday) -> onChecked(3, isChecked)
                    getString(R.string.thursday) -> onChecked(4, isChecked)
                    getString(R.string.friday) -> onChecked(5, isChecked)
                    getString(R.string.saturday) -> onChecked(6, isChecked)
                }
            }
        }
    }
}
