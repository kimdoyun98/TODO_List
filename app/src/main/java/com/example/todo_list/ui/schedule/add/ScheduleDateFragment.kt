package com.example.todo_list.ui.schedule.add

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.todo_list.databinding.AddScheduleDateLayoutBinding
import com.example.todo_list.ui.util.basefragment.DataBindingFragment

class ScheduleDateFragment :
    DataBindingFragment<AddScheduleDateLayoutBinding>(AddScheduleDateLayoutBinding::inflate) {
    private val viewModel: ScheduleAddViewModel by activityViewModels()
    private lateinit var callback: OnBackPressedCallback
    private lateinit var navController: NavController
    val backButton = {
        navController.popBackStack()
    }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel
        navController = findNavController()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    fun register() {
        viewModel.insert()
        activity?.finish()
    }
}
