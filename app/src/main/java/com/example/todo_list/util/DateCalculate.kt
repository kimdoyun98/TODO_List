package com.example.todo_list.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

object DateCalculate {
    private val calendar: Calendar = Calendar.getInstance()
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val todayDate = calendar.time.time

    fun getDDayString(date: LocalDate?): String {
        if (date == null) return "기간 없음"

        val endDate = date.calculateFormat()
        val today = selectionFormatter.format(LocalDate.now())

        return if (today == endDate) "D-Day"
        else if (today.toInt() > endDate.toInt()) "기간 지남"
        else "D-${(endDate.toInt() - todayDate) / (24 * 60 * 60 * 1000) + 1}"
    }

    fun getDDay(date: LocalDate?): Long {
        if (date == null) return -1

        val endDate = date.calculateFormat()
        val today = selectionFormatter.format(LocalDate.now())
        val day = (endDate.toInt() - todayDate) / (24 * 60 * 60 * 1000) + 1

        return if (today.toInt() > endDate.toInt() || day < 0) -1 else day
    }

    fun isWeekSchedule(date: LocalDate?): Boolean {
        if (date == null) return false

        val endDate = date.calculateFormat()
        val today = selectionFormatter.format(LocalDate.now())

        return if (today.toInt() > endDate.toInt()) false
        else if ((endDate.toInt() - todayDate) / (24 * 60 * 60 * 1000) + 1 <= 7) true
        else false
    }

    fun LocalDate.entityFormat(): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return this.format(formatter)
    }

    fun LocalDate.calculateFormat(): String {
        return selectionFormatter.format(this)
    }
}
