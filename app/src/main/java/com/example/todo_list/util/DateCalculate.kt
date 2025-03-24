package com.example.todo_list.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

object DateCalculate {
    private val calendar: Calendar = Calendar.getInstance()
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val todayDate = calendar.time.time

    fun getDDayString(date: String?): String {
        if (date.isNullOrBlank()) return "기간 없음"

        val endDate = SimpleDateFormat("yyyyMMdd").parse(date).time
        val today = selectionFormatter.format(LocalDate.now())

        return if (today == date) "D-Day"
        else if (today.toInt() > date.toInt()) "기간 지남"
        else "D-${(endDate - todayDate) / (24 * 60 * 60 * 1000) + 1}"
    }

    fun getDDay(date: String?): Long {
        if (date.isNullOrBlank()) return -1

        val endDate = SimpleDateFormat("yyyyMMdd").parse(date).time
        val today = selectionFormatter.format(LocalDate.now())
        val day = (endDate - todayDate) / (24 * 60 * 60 * 1000) + 1

        return if (today.toInt() > date.toInt() || day < 0) -1 else day
    }

    fun isWeekSchedule(date: String?): Boolean {
        if (date.isNullOrBlank()) return false

        val endDate = SimpleDateFormat("yyyyMMdd").parse(date).time
        val today = selectionFormatter.format(LocalDate.now())

        return if (today.toInt() > date.toInt()) false
        else if ((endDate - todayDate) / (24 * 60 * 60 * 1000) + 1 <= 7) true
        else false
    }
}
