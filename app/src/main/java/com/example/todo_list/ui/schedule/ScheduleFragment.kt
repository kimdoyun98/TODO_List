package com.example.todo_list.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todo_list.adapter.schedule.ScheduleAdapter
import com.example.todo_list.databinding.FragmentScheduleBinding
import com.example.todo_list.ui.schedule.DetailActivity.Companion.SCHEDULE_ENTITY
import com.example.todo_list.ui.schedule.add.ScheduleRegisterActivity
import com.example.todo_list.ui.util.BottomSheetDialog
import com.example.todo_list.ui.util.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
