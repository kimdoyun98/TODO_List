package com.project.ui.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.project.core.ui.R
import com.project.data.local.room.entity.ScheduleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class Calendar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var month: TextView
    private var monthRecyclerView: RecyclerView
    private var adapter: MonthAdapter
    private val _selectDay = MutableStateFlow<SelectDay?>(null)
    val selectDay = _selectDay.asStateFlow()

    private val _currentDate = MutableStateFlow<SelectDay?>(null)
    val currentDate = _currentDate.asStateFlow()

    init {
        inflate(context, R.layout.calendar_frame, this)
        month = findViewById(R.id.month)
        monthRecyclerView = findViewById(R.id.month_recycler)

        adapter = MonthAdapter(context) { year, month, day ->
            if (day == -1) _selectDay.value = null
            else _selectDay.value = SelectDay(year, month, day)
        }

        monthRecyclerView.adapter = adapter
        monthRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                val position = manager.findLastCompletelyVisibleItemPosition()

                if (position != -1) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.MONTH, position - 15)
                    month.text = "${calendar.get(Calendar.MONTH) + 1}월"

                    _currentDate.value = SelectDay(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        0
                    )
                }
            }
        })

        monthRecyclerView.scrollToPosition(15)

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(monthRecyclerView)
    }

    fun addSchedule(scheduleData: List<ScheduleEntity>) {
        adapter.addSchedule(scheduleData)
    }

    fun interface OnDayClickListener {
        fun onClick(year: Int, month: Int, day: Int)
    }

    data class SelectDay(
        val year: Int,
        val month: Int,
        val day: Int
    )
}
