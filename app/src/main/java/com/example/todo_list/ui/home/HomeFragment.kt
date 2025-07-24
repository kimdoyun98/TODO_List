package com.example.todo_list.ui.home

import androidx.fragment.app.viewModels
import com.example.todo_list.databinding.FragmentHomeBinding
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
