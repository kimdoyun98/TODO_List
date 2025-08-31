package com.project.ui.view.bindingadapter

import androidx.databinding.BindingAdapter
import com.project.model.Routine
import com.project.ui.view.bullet_point.BulletPoint
import com.project.ui.view.bullet_point.ProgressBulletPoint

object BulletPointBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:items", "app:item_index", requireAll = true)
    fun bulletPointBinding(view: BulletPoint, items: List<Routine>, index: Int) {
        setPosition(view, items, index)
    }

    @JvmStatic
    @BindingAdapter("app:items", "app:item_index", requireAll = true)
    fun progressBulletPointState(
        view: ProgressBulletPoint,
        items: List<Routine>,
        index: Int
    ) {
        setPosition(view, items, index)
        view.setProgressState(
            when (items[index].success) {
                true -> ProgressBulletPoint.ProgressState.Success
                false -> ProgressBulletPoint.ProgressState.Fail
                null -> {
                    if (index == 0 || items[index - 1].success != null) ProgressBulletPoint.ProgressState.Do
                    else ProgressBulletPoint.ProgressState.Wait
                }
            }
        )
    }

    private fun setPosition(view: BulletPoint, items: List<Routine>, index: Int) {
        view.setPosition(
            when (index) {
                0 -> {
                    if (items.size == 1) BulletPoint.Position.ONE
                    else BulletPoint.Position.START
                }

                items.lastIndex -> {
                    BulletPoint.Position.END
                }

                else -> {
                    BulletPoint.Position.MID
                }
            }
        )
    }
}
