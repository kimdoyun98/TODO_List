package com.example.todo_list.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class DateCalculate {
    private val calendar: Calendar = Calendar.getInstance()
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val todayDate = calendar.time.time

    fun getDDayString(deadline: String?): String {
        if (deadline.isNullOrBlank()) return "기간 없음"

        val endDate = SimpleDateFormat("yyyyMMdd").parse(deadline).time
        val today = selectionFormatter.format(LocalDate.now())

        return if (today == deadline) "D-Day"
        else if (today.toInt() > deadline.toInt()) "기간 지남"
        else "D-${(endDate - todayDate) / (24 * 60 * 60 * 1000) + 1}"
    }

    fun getDDay(deadline: String?): Long {
        if (deadline.isNullOrBlank()) return -1
        val endDate = SimpleDateFormat("yyyyMMdd").parse(deadline).time
        val day = (endDate - todayDate) / (24 * 60 * 60 * 1000) + 1

        return if (day < 0) -1 else day
    }

    fun isWeekSchedule(deadline: String?): Boolean {
        if (deadline.isNullOrBlank()) return false

        val endDate = SimpleDateFormat("yyyyMMdd").parse(deadline).time
        val today = selectionFormatter.format(LocalDate.now())

        return if (today.toInt() > deadline.toInt()) false
        else if ((endDate - todayDate) / (24 * 60 * 60 * 1000) + 1 <= 7) true
        else false
    }
}
