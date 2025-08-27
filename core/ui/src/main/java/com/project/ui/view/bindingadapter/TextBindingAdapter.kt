package com.project.ui.view.bindingadapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.project.data.local.room.entity.RoutineEntity
import com.project.ui.date.DateCalculate
import com.project.ui.date.DateCalculate.entityFormat
import java.time.LocalDate

object TextBindingAdapter {

    @JvmStatic
    @BindingAdapter("d-day")
    fun setDDayText(view: TextView, date: LocalDate?) {
        date?.let {
            view.text = DateCalculate.getDDayString(date)
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun dateText(view: TextView, date: LocalDate?) {
        view.text = date?.entityFormat()
    }

    @JvmStatic
    @BindingAdapter("entity", "days_text", "daily_text", requireAll = true)
    fun setTextColorWithDays(
        view: TextView,
        routineEntity: RoutineEntity,
        days: String,
        daily: String
    ) {
        val spannableString = SpannableString(days)

        val checkedDayIndex = ArrayList<Int>()
        for (i in 0..6) {
            if (routineEntity.day?.get(i)!!) checkedDayIndex.add(i)
        }

        if (checkedDayIndex.size == 7) {
            view.text = daily
            view.setTextColor(Color.BLUE)
        } else {
            for (i in checkedDayIndex) {
                val index = when (i) {
                    0 -> 14
                    1 -> 2
                    2 -> 4
                    3 -> 6
                    4 -> 8
                    5 -> 10
                    else -> 12
                }

                spannableString.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    index - 1,
                    index,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                view.text = spannableString
            }
        }
    }
}
