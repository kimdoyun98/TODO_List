package com.project.schedule.edit

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.project.feature.schedule.databinding.AddScheduleDateLayoutBinding
import com.project.ui.base.DataBindingFragment

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

        fragmentScope {
            viewModel.finish.collect {
                if (it) activity?.finish()
            }
        }
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
    }
}
