package com.example.todo_list.ui.schedule.add

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.todo_list.R
import com.example.todo_list.databinding.AddScheduleDateLayoutBinding
import com.example.todo_list.ui.util.basefragment.DataBindingFragment

class ScheduleDateFragment :
    DataBindingFragment<AddScheduleDateLayoutBinding>(AddScheduleDateLayoutBinding::inflate) {
    private val viewModel: ScheduleAddViewModel by activityViewModels()
    private lateinit var navController: NavController
    val backButton = { navController.navigate(R.id.action_schedule_add_date_to_schedule_add_title) }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel
        navController = findNavController()
    }
}
