package com.example.todo_list.Fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.*
import com.example.todo_list.Adapter.CalendarEventsAdapter
import com.example.todo_list.Common.DiffUtilCallBackTODO
import com.example.todo_list.data.ToDoEntity
import com.example.todo_list.databinding.CalendarDayBinding
import com.example.todo_list.databinding.CalendarHeaderBinding
import com.example.todo_list.databinding.FragmentCalendarBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment(R.layout.fragment_calendar) {
    private lateinit var binding : FragmentCalendarBinding
    private val viewModel : ToDoViewModel by viewModels()

    private val calendarEventsAdapter = CalendarEventsAdapter {}

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val selectionFormatter2 = DateTimeFormatter.ofPattern("yyyyMMdd")

    //override val titleRes: Int = R.string.calender_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

        /**
         * ????????? ???????????? ?????? ?????? Recyclerview
         */
        binding.exThreeRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = calendarEventsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }


        /**
         * com.kizitonwose.calendarview.CalendarView
         * Calendar setting
         */
        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.exThreeCalendar.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            binding.exThreeCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }
        /**
         * com.kizitonwose.calendarview.CalendarView
         * ????????? ????????? ?????? xml setting
         */
        binding.exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    var date : String = selectionFormatter2.format(day.date)
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.today_bg)
                            viewModel.getOnDate(date).observe(viewLifecycleOwner) { data ->
                                updateAdapterForDate(today!!, data)
                            }
                        }
                        selectedDate -> {
                            date = date.substring(0 until 8)
                            viewModel.getOnDate(date).observe(viewLifecycleOwner) { data ->
                                updateAdapterForDate(selectedDate!!, data)
                            }

                            textView.setTextColorRes(R.color.blue)
                            textView.setBackgroundResource(R.drawable.selected_bg)
                        }
                        // ???????????? ?????? ?????????
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                        }
                    }
                } else {
                    textView.makeInVisible()
                    //dotView.makeInVisible()
                }
            }
        }

        /**
         * Toolbar setting
         */
        binding.exThreeCalendar.monthScrollListener = {
            homeActivityToolbar.title = if (it.year == today.year) {
            titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }

            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }

        /**
         * com.kizitonwose.calendarview.CalendarView
         */
        binding.exThreeCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.first().toString()
                        tv.setTextColorRes(R.color.black)
                    }
                }
            }
        }
    }

    /**
     * ?????? ?????? ??? ??????
     */
    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
            binding.exThreeCalendar.notifyDateChanged(date)
        }
    }

    /**
     * EventAdapter ????????? ?????????
     */
    private fun updateAdapterForDate(date: LocalDate, data : List<ToDoEntity>) {
        calendarEventsAdapter.apply {
            val diffCallback = DiffUtilCallBackTODO(list, data)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            list.clear()
            list.addAll(data)
            diffResult.dispatchUpdatesTo(this@apply)
        }
        binding.exThreeSelectedDateText.text = selectionFormatter.format(date)
    }
}