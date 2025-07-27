package com.example.todo_list.ui.schedule.add

import androidx.fragment.app.activityViewModels
import com.example.todo_list.databinding.AddScheduleTitleLayoutBinding
import com.example.todo_list.ui.util.basefragment.DataBindingFragment

class ScheduleTitleFragment :
    DataBindingFragment<AddScheduleTitleLayoutBinding>(AddScheduleTitleLayoutBinding::inflate) {
    private val viewModel: ScheduleAddViewModel by activityViewModels()
    val finish = { activity?.finish() }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel
    }
}
