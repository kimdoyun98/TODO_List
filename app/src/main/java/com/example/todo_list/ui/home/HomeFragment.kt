package com.example.todo_list.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.todo_list.adapter.home.HomeRoutineAdapter
import com.example.todo_list.adapter.home.HomeScheduleAdapter
import com.example.todo_list.databinding.FragmentHomeBinding
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import com.example.todo_list.util.DateCalculate.getDDay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        initScheduleViewPager()
        initTodayRoutines()
    }

    private fun initScheduleViewPager() {
        val adapter = HomeScheduleAdapter()
        binding.homeScheduleVp.adapter = adapter

        fragmentScope {
            viewModel.getScheduleAll.collect { scheduleData ->
                if (scheduleData.isEmpty()) binding.homeScheduleVp.visibility = View.GONE
                else {
                    val filterScheduleList = scheduleData.filter { scheduleEntity ->
                        getDDay(scheduleEntity.start_date) >= 0
                    }

                    binding.homeScheduleVp.visibility = View.VISIBLE
                    adapter.submitList(filterScheduleList)
                }
            }
        }

        binding.homeScheduleVp.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.homeScheduleVp.setPageTransformer(compositePageTransformer)
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
            viewModel.getRoutineAll.collect { routineData ->
                adapter.submitList(routineData.sortedBy { it.time })
            }
        }
    }
}
