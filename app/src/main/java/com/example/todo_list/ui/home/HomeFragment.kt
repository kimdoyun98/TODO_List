package com.example.todo_list.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.todo_list.adapter.home.HomeRoutineAdapter
import com.example.todo_list.databinding.FragmentHomeBinding
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initTodayRoutines()
    }

    private fun initTodayRoutines() {
        val adapter = HomeRoutineAdapter(
            { id ->
                viewModel.getRoutineDetails(id)
            },
            viewLifecycleOwner.lifecycleScope
        )
        val todayRecyclerView = binding.todayRecyclerview
        todayRecyclerView.adapter = adapter

        fragmentScope {
            viewModel.todayRoutine.collect { routineData ->
                adapter.submitList(routineData.sortedBy { it.time })
            }
        }
    }
}
