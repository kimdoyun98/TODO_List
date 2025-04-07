package com.example.todo_list.ui.schedule

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todo_list.util.DatePicker
import com.example.todo_list.R
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.ActivityRegisterBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getSerializableExtra(SCHEDULE_ENTITY) as ScheduleEntity

        val categoryAdapter = ArrayAdapter.createFromResource(this,
            R.array.categoryName, android.R.layout.simple_spinner_item)

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.todoEntity = data

        binding.calendar.setOnClickListener{
            DatePicker(this, binding.startDate)
        }

        binding.cancelButton.setOnClickListener{ finish() }
        binding.registerButton.setOnClickListener {
            val viewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]

            viewModel.update(
                ScheduleEntity(
                    id = data.id,
                    title = binding.title.text.toString(),
                    content = binding.content.text.toString(),
                    start_date = binding.startDate.text.toString(),
                    deadline_date = binding.endDate.text.toString(),
                    color = data.color,
                    success = false
                )
            )
            finish()
        }
    }

    companion object{
        const val SCHEDULE_ENTITY = "data"
    }
}