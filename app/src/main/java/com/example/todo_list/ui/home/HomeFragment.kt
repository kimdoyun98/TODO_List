package com.example.todo_list.ui.home

import android.content.Context
import androidx.fragment.app.viewModels
import com.example.todo_list.databinding.FragmentHomeBinding
import com.example.todo_list.ui.util.StateDialog
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    val openDialog = { position: Int, title: String?, context: Context ->
        StateDialog.showDialog(
            context = context,
            title = title,
            onClickPositiveButton = { viewModel.updateToRoutine(position, true) },
            onClickNegativeButton = { viewModel.updateToRoutine(position, false) }
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.fragment = this@HomeFragment
    }
}
