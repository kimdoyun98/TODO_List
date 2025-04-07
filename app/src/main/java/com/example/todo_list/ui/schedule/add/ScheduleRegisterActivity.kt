package com.example.todo_list.ui.schedule.add

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_list.R
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.ActivityRegisterBinding
import com.example.todo_list.ui.schedule.ScheduleViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ScheduleRegisterActivity : AppCompatActivity() {
    private val viewModel : ScheduleViewModel by viewModels()
    private lateinit var binding : ActivityRegisterBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * DatePicker 사용 / 날짜 선택 시 TextView에 해당 날짜 입력
         */
        binding.calendar.setOnClickListener{
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(getString(R.string.date_picker_title))
                .build()
            dateRangePicker.show(supportFragmentManager, DATE_PICKER)
            dateRangePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it?.first ?: 0
                binding.startDate.text = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()

                calendar.timeInMillis = it?.second ?: 0
                binding.endDate.text = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
            }
        }

        // 취소 버튼
        binding.cancelButton.setOnClickListener{ finish() }

        // 등록 버튼
        binding.registerButton.setOnClickListener{

            val newTodo = ScheduleEntity(
                title = binding.title.text.toString(),
                content = binding.content.text.toString(),
                start_date = binding.startDate.text.toString(),
                deadline_date = binding.endDate.text.toString(),
                color = getRandomColor(),
                success = false
            )
            viewModel.insert(newTodo)
            finish()
        }
    }


    private fun getRandomColor(): Int {
        val range = (0..255)
        return Color.argb(255, range.random(), range.random(), range.random())
    }

    companion object{
        private const val DATE_PICKER = "date picker"
    }
}
