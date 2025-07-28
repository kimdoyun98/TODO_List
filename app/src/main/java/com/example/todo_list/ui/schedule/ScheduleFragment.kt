package com.example.todo_list.ui.schedule

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.FragmentScheduleBinding
import com.example.todo_list.ui.schedule.add.ScheduleRegisterActivity
import com.example.todo_list.ui.util.StateDialog
import com.example.todo_list.ui.util.basefragment.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :
    DataBindingFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {
    private val viewModel: ScheduleViewModel by viewModels()
    val showDialog = { scheduleEntity: ScheduleEntity ->
        StateDialog.showDialog(
            context = requireContext(),
            title = scheduleEntity.title,
            negativeText = "삭제",
            onClickPositiveButton = { viewModel.success(scheduleEntity.id) },
            onClickNegativeButton = { viewModel.delete(scheduleEntity.id) }
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment = this
    }

    fun addSchedule() {
        val intent = Intent(context, ScheduleRegisterActivity::class.java)
        startActivity(intent)
    }
}
