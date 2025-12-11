package com.project.schedule

import android.content.Intent
import androidx.fragment.app.viewModels
import com.project.feature.schedule.databinding.FragmentScheduleBinding
import com.project.model.Schedule
import com.project.schedule.edit.ScheduleRegisterActivity
import com.project.ui.base.DataBindingFragment
import com.project.ui.dialog.StateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ScheduleFragment :
    DataBindingFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {
    private val viewModel: ScheduleViewModel by viewModels()
    val showDialog = { scheduleEntity: Schedule ->
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
