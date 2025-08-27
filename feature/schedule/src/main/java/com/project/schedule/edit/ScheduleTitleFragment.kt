package com.project.schedule.edit

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.project.feature.schedule.R
import com.project.feature.schedule.databinding.AddScheduleTitleLayoutBinding
import com.project.ui.base.DataBindingFragment

class ScheduleTitleFragment :
    DataBindingFragment<AddScheduleTitleLayoutBinding>(AddScheduleTitleLayoutBinding::inflate) {
    private val viewModel: ScheduleAddViewModel by activityViewModels()
    private lateinit var navController: NavController
    val finish = { activity?.finish() }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel
        navController = findNavController()
    }

    fun onNextClick() {
        navController.navigate(R.id.action_schedule_add_title_to_schedule_add_date)
    }
}
