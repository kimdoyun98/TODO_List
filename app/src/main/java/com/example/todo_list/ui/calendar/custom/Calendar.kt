package com.example.todo_list.ui.calendar.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import java.util.Calendar

class Calendar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var month: TextView
    private var monthRecyclerView: RecyclerView
    private var adapter: MonthAdapter

    init {
        inflate(context, R.layout.calendar_frame, this)
        month = findViewById(R.id.month)
        monthRecyclerView = findViewById(R.id.month_recycler)

        adapter = MonthAdapter()
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
                }
            }
        })

        monthRecyclerView.scrollToPosition(15)

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(monthRecyclerView)
    }
}
