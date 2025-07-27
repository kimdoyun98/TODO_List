package com.example.todo_list.ui.schedule.add

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.todo_list.R
import com.example.todo_list.databinding.ActivityRegisterBinding
import com.example.todo_list.ui.schedule.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleRegisterActivity : AppCompatActivity() {
    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.add_schedule_fragment) as NavHostFragment
        val navController = navHostFragment.navController


    }


    private fun getRandomColor(): Int {
        val range = (0..255)
        return Color.argb(255, range.random(), range.random(), range.random())
    }

    companion object {
        private const val DATE_PICKER = "date picker"
    }
}
