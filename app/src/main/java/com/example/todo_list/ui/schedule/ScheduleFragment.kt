package com.example.todo_list.ui.schedule

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.todo_list.adapter.schedule.ScheduleAdapter
import com.example.todo_list.databinding.FragmentScheduleBinding
import com.example.todo_list.ui.schedule.DetailActivity.Companion.SCHEDULE_ENTITY
import com.example.todo_list.ui.schedule.add.ScheduleRegisterActivity
import com.example.todo_list.ui.util.BottomSheetDialog
import com.example.todo_list.ui.util.Category
import com.example.todo_list.ui.util.basefragment.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :
    DataBindingFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {
    private val viewModel: ScheduleViewModel by viewModels()

    override fun initView() {
        binding.todoViewModel = viewModel

        initRecyclerViewAdapter()
        addScheduleListener()
    }

    private fun initRecyclerViewAdapter() {
        val adapter = ScheduleAdapter(
            showDialog = { scheduleEntity ->
                BottomSheetDialog(
                    context = requireContext(),
                    entity = scheduleEntity,
                    category = Category.SCHEDULE,
                    onClickUpdate = {
                        val intent = Intent(requireContext(), DetailActivity::class.java)
                        intent.putExtra(SCHEDULE_ENTITY, scheduleEntity)
                        startActivity(intent)
                    },
                    onClickDelete = { viewModel.delete(scheduleEntity.id) },
                    onClickDone = { viewModel.success(scheduleEntity.id) }
                )
            }
        )
        binding.recyclerview.adapter = adapter

        viewModel.getAll.observe(viewLifecycleOwner) { list ->
            viewModel.sortFilter.observe(viewLifecycleOwner) { filter ->
                when (filter) {
                    ScheduleViewModel.LATEST -> {
                        adapter.submitList(list.sortedByDescending { it.id })
                    }

                    ScheduleViewModel.DEADLINE -> {
                        adapter.submitList(
                            list
                                .sortedByDescending { it.deadline_date }
                                .reversed()
                        )
                    }
                }
            }
        }
    }

    private fun addScheduleListener() {
        binding.addButton.setOnClickListener {
            val intent = Intent(context, ScheduleRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
