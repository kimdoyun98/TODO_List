package com.example.todo_list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todo_list.adapter.HomeCycleAdapter
import com.example.todo_list.adapter.HomeTodoAdapter
import com.example.todo_list.HomeViewModel
import com.example.todo_list.data.CycleEntity
import com.example.todo_list.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()
    private var todayData : MutableList<CycleEntity> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        /**
         * 오늘 할 일
         */
        val adapter = HomeCycleAdapter(requireContext(), viewModel)
        val todayRecyclerView = binding.todayRecyclerview
        todayRecyclerView.adapter = adapter

        viewModel.getDay().observe(viewLifecycleOwner){
            viewModel.getAll().observe(viewLifecycleOwner) { dataList ->
                todayData.clear()
                for (data in dataList){
                    if(data.day?.get(it-1) == true) todayData.add(data)
                }
                adapter.setData(todayData)
            }
        }
        /**
         * 일정
         */
        val personalAdapter = HomeTodoAdapter(requireContext(), viewModel)
        val personalRecyclerView = binding.personalRecyclerview
        personalRecyclerView.adapter = personalAdapter

        viewModel.todoAll().observe(viewLifecycleOwner){ dataList ->
            personalAdapter.setData(dataList)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHomeBinding.inflate(layoutInflater).root
    }
}