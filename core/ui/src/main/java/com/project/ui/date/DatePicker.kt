package com.project.ui.date

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import java.util.*

class DatePicker(context: Context, textView: TextView) {
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)

    init {
        val datePicker = DatePickerDialog(context, { _, year, month, day ->
            var a = ""
            var b = ""
            if((month + 1) < 10) a = "0"
            if (day < 10) b = "0"
            textView.text =
                year.toString() + "${a}" + (month + 1).toString() + "${b}" + day.toString()
        }, year, month, day)
        datePicker.show()
    }
}
