package com.example.todo_list.ui.schedule

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.databinding.FragmentScheduleBinding
import com.example.todo_list.ui.schedule.add.ScheduleRegisterActivity
import com.example.todo_list.ui.util.BottomSheetDialog
import com.example.todo_list.ui.util.Category
import com.example.todo_list.ui.util.basefragment.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :
    DataBindingFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {
    private val viewModel: ScheduleViewModel by viewModels()
    val showDialog = { scheduleEntity: ScheduleEntity ->
        BottomSheetDialog(
            context = requireContext(),
            entity = scheduleEntity,
            category = Category.SCHEDULE,
            onClickUpdate = {
                //TODO Update
            },
            onClickDelete = { viewModel.delete(scheduleEntity.id) },
            onClickDone = { viewModel.success(scheduleEntity.id) }
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment = this

        addScheduleListener()
    }

    private fun addScheduleListener() {
        binding.addButton.setOnClickListener {
            val intent = Intent(context, ScheduleRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
